package com.g.commons.web;

import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.PUT;

import java.io.Serializable;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.repository.support.Repositories;
import org.springframework.data.repository.support.RepositoryInvoker;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.event.*;
import org.springframework.data.rest.core.mapping.RepositoryResourceMappings;
import org.springframework.data.rest.core.mapping.ResourceType;
import org.springframework.data.rest.webmvc.*;
import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.data.rest.webmvc.support.BackendId;
import org.springframework.data.rest.webmvc.support.DefaultedPageable;
import org.springframework.data.rest.webmvc.support.ETag;
import org.springframework.data.rest.webmvc.support.ETagDoesntMatchException;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import com.g.commons.model.PagedModelLite;

/**
 * 对于关联对象查询会出错
 * Throw new ResourceNotFoundException() 等时，错误处理框架会捕获，加上了不必要的处理
 */

@BasePathAwareController
public class CustomRestController implements ApplicationEventPublisherAware {
    public static final String CUSTOM_REST_ROOT_MAPPING = "/c4m";
    public static final String RESOURCE_CUSTOM_REST_MAPPING = CUSTOM_REST_ROOT_MAPPING + "/{repository}";

    public static final boolean oneIndexedParameters = true;

    private static final String ACCEPT_HEADER = "Accept";

    private final RepositoryRestConfiguration configuration;
    private final RepositoryResourceMappings mappings;
    private final Repositories repositories;
    private final HttpHeadersPreparer headersPreparer;
    private final ResourceStatus resourceStatus;
    private final PluginRegistry<BackendIdConverter, Class<?>> idConverters;

    private ApplicationEventPublisher publisher;

    /**
     * Wire up the controller with a copy of {@link RepositoryRestConfiguration}.
     *
     * @param configuration must not be {@literal null}.
     * @param mappings      must not be {@literal null}.
     * @param repositories  must not be {@literal null}.
     */
    @Autowired
    public CustomRestController(RepositoryRestConfiguration configuration,
                                RepositoryResourceMappings mappings,
                                Repositories repositories,
                                HttpHeadersPreparer headersPreparer,
                                PluginRegistry<BackendIdConverter, Class<?>> idConverters,
                                PageableHandlerMethodArgumentResolver pageableResolver) {

        Assert.notNull(configuration, "RepositoryRestConfiguration must not be null!");
        Assert.notNull(mappings, "RepositoryResourceMappings must not be null!");
        Assert.notNull(repositories, "Repositories must not be null!");
        Assert.notNull(headersPreparer, "HttpHeadersPreparer must not be null!");

        this.configuration = configuration;
        this.mappings = mappings;
        this.repositories = repositories;
        this.headersPreparer = headersPreparer;
        this.resourceStatus = ResourceStatus.of(headersPreparer);
        this.idConverters = idConverters;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.context.ApplicationEventPublisherAware#setApplicationEventPublisher(org.springframework.context.ApplicationEventPublisher)
     */
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    /**
     * <code>GET /{repository}</code> - Returns the collection resource (paged or unpaged).
     *
     * @param resourceInformation
     * @param pageable
     * @param sort
     * @param assembler
     * @return
     * @throws ResourceNotFoundException
     * @throws HttpRequestMethodNotSupportedException
     */
    @ResponseBody
    @RequestMapping(value = RESOURCE_CUSTOM_REST_MAPPING, method = RequestMethod.GET)
    public Iterable<?> getCollectionResource(@QuerydslPredicate RootResourceInformation resourceInformation,
                                             DefaultedPageable pageable, Sort sort, PersistentEntityResourceAssembler assembler)
            throws ResourceNotFoundException, HttpRequestMethodNotSupportedException {

        resourceInformation.verifySupportedMethod(HttpMethod.GET, ResourceType.COLLECTION);

        RepositoryInvoker invoker = resourceInformation.getInvoker();

        if (null == invoker) {
            throw new ResourceNotFoundException();
        }

        Iterable<?> results = pageable.getPageable() != null //
                ? invoker.invokeFindAll(pageable.getPageable()) //
                : invoker.invokeFindAll(sort);

        return results instanceof PageImpl<?>
                ? new PagedModelLite<>((PageImpl) results, oneIndexedParameters)
                : results;
    }


    /**
     * <code>POST /{repository}</code> - Creates a new entity instances from the collection resource.
     *
     * @param resourceInformation
     * @param payload
     * @param assembler
     * @param acceptHeader
     * @return
     * @throws HttpRequestMethodNotSupportedException
     */
    @ResponseBody
    @RequestMapping(value = RESOURCE_CUSTOM_REST_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<?> postCollectionResource(RootResourceInformation resourceInformation,
                                                    PersistentEntityResource payload, PersistentEntityResourceAssembler assembler,
                                                    @RequestHeader(value = ACCEPT_HEADER, required = false) String acceptHeader)
            throws HttpRequestMethodNotSupportedException {

        resourceInformation.verifySupportedMethod(HttpMethod.POST, ResourceType.COLLECTION);

        return createAndReturn(payload.getContent(), resourceInformation.getInvoker(), assembler,
                true);
    }

    /**
     * <code>DELETE /{repository}?ids=recordId,recordId</code> - Deletes the entity backing the item resource.
     *
     * @param resourceInformation
     * @param ids
     * @param eTag
     * @return
     * @throws ResourceNotFoundException
     * @throws HttpRequestMethodNotSupportedException
     * @throws ETagDoesntMatchException
     */
    @RequestMapping(value = RESOURCE_CUSTOM_REST_MAPPING, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCollectionResource(RootResourceInformation resourceInformation,
                                                      @RequestParam String ids,
                                                      ETag eTag)
            throws ResourceNotFoundException, HttpRequestMethodNotSupportedException {

        resourceInformation.verifySupportedMethod(HttpMethod.DELETE, ResourceType.ITEM);

        if (!StringUtils.hasText(ids)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        RepositoryInvoker invoker = resourceInformation.getInvoker();
        BackendIdConverter pluginFor = idConverters.getPluginFor(resourceInformation.getDomainType())
                .orElse(BackendIdConverter.DefaultIdConverter.INSTANCE);

        for (String idSource : ids.split(",")) {
            Serializable id = pluginFor.fromRequestId(idSource, resourceInformation.getDomainType());
            Optional<Object> domainObj = invoker.invokeFindById(id);

            domainObj.map(it -> {
                PersistentEntity<?, ?> entity = resourceInformation.getPersistentEntity();
                eTag.verify(entity, it);
                publisher.publishEvent(new BeforeDeleteEvent(it));
                invoker.invokeDeleteById(entity.getIdentifierAccessor(it).getIdentifier());
                publisher.publishEvent(new AfterDeleteEvent(it));
                return it;
            }).orElseThrow(() -> new ResourceNotFoundException());
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * <code>GET /{repository}/{id}</code> - Returns a single entity.
     *
     * @param resourceInformation
     * @param id
     * @return
     * @throws HttpRequestMethodNotSupportedException
     */
    @RequestMapping(value = RESOURCE_CUSTOM_REST_MAPPING + "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getItemResource(RootResourceInformation resourceInformation,
                                             @BackendId Serializable id,
                                             final PersistentEntityResourceAssembler assembler,
                                             @RequestHeader HttpHeaders headers)
            throws HttpRequestMethodNotSupportedException {
        return getItemResource(resourceInformation, id)
                .map(it -> {
                    Hibernate.initialize(it);
                    PersistentEntity<?, ?> entity = resourceInformation.getPersistentEntity();
                    return resourceStatus.getStatusAndHeaders(headers, it, entity).toResponseEntity(it);
                })
                .orElseThrow(() -> new ResourceNotFoundException());
    }

    /**
     * <code>PUT /{repository}/{id}</code> - Updates an existing entity or creates one at exactly that place.
     *
     * @param resourceInformation
     * @param payload
     * @param id
     * @param assembler
     * @param eTag
     * @param acceptHeader
     * @return
     * @throws HttpRequestMethodNotSupportedException
     */
    @RequestMapping(value = RESOURCE_CUSTOM_REST_MAPPING + "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> putItemResource(RootResourceInformation resourceInformation,
                                             PersistentEntityResource payload,
                                             @BackendId Serializable id,
                                             PersistentEntityResourceAssembler assembler,
                                             ETag eTag,
                                             @RequestHeader(value = ACCEPT_HEADER, required = false) String acceptHeader)
            throws HttpRequestMethodNotSupportedException {

        resourceInformation.verifySupportedMethod(HttpMethod.PUT, ResourceType.ITEM);

        if (payload.isNew()) {
            resourceInformation.verifyPutForCreation();
        }

        RepositoryInvoker invoker = resourceInformation.getInvoker();
        Object objectToSave = payload.getContent();
        eTag.verify(resourceInformation.getPersistentEntity(), objectToSave);

        return payload.isNew() ? createAndReturn(objectToSave, invoker, assembler, true)
                : saveAndReturn(objectToSave, invoker, PUT, assembler, true);
    }

    /**
     * <code>PATCH /{repository}/{id}</code> - Updates an existing entity or creates one at exactly that place.
     *
     * @param resourceInformation
     * @param payload
     * @param id
     * @param assembler
     * @param eTag,
     * @param acceptHeader
     * @return
     * @throws HttpRequestMethodNotSupportedException
     * @throws ResourceNotFoundException
     * @throws ETagDoesntMatchException
     */
    @RequestMapping(value = RESOURCE_CUSTOM_REST_MAPPING + "/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<?> patchItemResource(RootResourceInformation resourceInformation,
                                               PersistentEntityResource payload,
                                               @BackendId Serializable id,
                                               PersistentEntityResourceAssembler assembler,
                                               ETag eTag,
                                               @RequestHeader(value = ACCEPT_HEADER, required = false) String acceptHeader)
            throws HttpRequestMethodNotSupportedException, ResourceNotFoundException {

        resourceInformation.verifySupportedMethod(HttpMethod.PATCH, ResourceType.ITEM);

        Object domainObject = payload.getContent();

        eTag.verify(resourceInformation.getPersistentEntity(), domainObject);

        return saveAndReturn(domainObject, resourceInformation.getInvoker(), PATCH, assembler, true);
    }

    /**
     * <code>DELETE /{repository}/{id}</code> - Deletes the entity backing the item resource.
     *
     * @param resourceInformation
     * @param id
     * @param eTag
     * @return
     * @throws ResourceNotFoundException
     * @throws HttpRequestMethodNotSupportedException
     * @throws ETagDoesntMatchException
     */
    @RequestMapping(value = RESOURCE_CUSTOM_REST_MAPPING + "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteItemResource(RootResourceInformation resourceInformation,
                                                @BackendId Serializable id,
                                                ETag eTag)
            throws ResourceNotFoundException, HttpRequestMethodNotSupportedException {

        resourceInformation.verifySupportedMethod(HttpMethod.DELETE, ResourceType.ITEM);

        RepositoryInvoker invoker = resourceInformation.getInvoker();
        Optional<Object> domainObj = invoker.invokeFindById(id);

        return domainObj.map(it -> {

            PersistentEntity<?, ?> entity = resourceInformation.getPersistentEntity();

            eTag.verify(entity, it);

            publisher.publishEvent(new BeforeDeleteEvent(it));
            invoker.invokeDeleteById(entity.getIdentifierAccessor(it).getIdentifier());
            publisher.publishEvent(new AfterDeleteEvent(it));

            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);

        }).orElseThrow(() -> new ResourceNotFoundException());
    }

    /**
     * Merges the given incoming object into the given domain object.
     *
     * @param domainObject
     * @param invoker
     * @param httpMethod
     * @return
     */
    private ResponseEntity<?> saveAndReturn(Object domainObject, RepositoryInvoker invoker,
                                            HttpMethod httpMethod, PersistentEntityResourceAssembler assembler, boolean returnBody) {

        publisher.publishEvent(new BeforeSaveEvent(domainObject));
        Object obj = invoker.invokeSave(domainObject);
        publisher.publishEvent(new AfterSaveEvent(obj));

        PersistentEntityResource resource = assembler.toFullResource(obj);
        HttpHeaders headers = headersPreparer.prepareHeaders(Optional.of(resource));

        if (returnBody) {
            return toResponseEntity(HttpStatus.OK, headers, Optional.of(obj));
        } else {
            return toEmptyResponse(HttpStatus.NO_CONTENT, headers);
        }
    }

    /**
     * Triggers the creation of the domain object and renders it into the response if needed.
     *
     * @param domainObject
     * @param invoker
     * @return
     */
    private ResponseEntity<?> createAndReturn(Object domainObject, RepositoryInvoker invoker,
                                              PersistentEntityResourceAssembler assembler, boolean returnBody) {

        publisher.publishEvent(new BeforeCreateEvent(domainObject));
        Object savedObject = invoker.invokeSave(domainObject);
        publisher.publishEvent(new AfterCreateEvent(savedObject));

        Optional<PersistentEntityResource> resource = Optional
                .ofNullable(returnBody ? assembler.toFullResource(savedObject) : null);
        HttpHeaders headers = headersPreparer.prepareHeaders(resource);

        return toResponseEntity(HttpStatus.CREATED, headers, Optional.ofNullable(returnBody ? savedObject : null));
    }

    /**
     * Returns the object backing the item resource for the given {@link RootResourceInformation} and id.
     *
     * @param resourceInformation
     * @param id
     * @return
     * @throws HttpRequestMethodNotSupportedException
     * @throws {@link                                 ResourceNotFoundException}
     */
    private Optional<Object> getItemResource(RootResourceInformation resourceInformation, Serializable id)
            throws HttpRequestMethodNotSupportedException, ResourceNotFoundException {

        resourceInformation.verifySupportedMethod(HttpMethod.GET, ResourceType.ITEM);

        return resourceInformation.getInvoker().invokeFindById(id);
    }

    static <T> ResponseEntity<T> toResponseEntity(
            HttpStatus status, HttpHeaders headers, Optional<T> resource) {

        HttpHeaders hdrs = new HttpHeaders();

        if (headers != null) {
            hdrs.putAll(headers);
        }

        return new ResponseEntity<T>(resource.orElse(null), hdrs, status);
    }


    /**
     * Wrap a resource as a {@link ResponseEntity} and attach given headers and status.
     *
     * @param status
     * @param headers
     * @param resource
     * @return
     */
    static <T> ResponseEntity<T> toResponseEntity(
            HttpStatus status, HttpHeaders headers, T resource) {

        Assert.notNull(status, "Http status must not be null!");
        Assert.notNull(headers, "Http headers must not be null!");
        Assert.notNull(resource, "Payload must not be null!");

        return toResponseEntity(status, headers, Optional.of(resource));
    }

    /**
     * Return an empty response that is only comprised of a status
     *
     * @param status
     * @return
     */
    static ResponseEntity<?> toEmptyResponse(HttpStatus status) {
        return toEmptyResponse(status, new HttpHeaders());
    }

    /**
     * Return an empty response that is only comprised of headers and a status
     *
     * @param status
     * @param headers
     * @return
     */
    static ResponseEntity<?> toEmptyResponse(HttpStatus status, HttpHeaders headers) {
        return toResponseEntity(status, headers, Optional.empty());
    }

}
