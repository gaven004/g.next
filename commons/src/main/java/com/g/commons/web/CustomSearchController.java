//package com.g.commons.web;
//
//import static com.g.commons.web.CustomRestController.oneIndexedParameters;
//
//import java.lang.reflect.Method;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.MethodParameter;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.mapping.PersistentEntity;
//import org.springframework.data.repository.query.Param;
//import org.springframework.data.repository.support.RepositoryInvoker;
//import org.springframework.data.rest.core.mapping.MethodResourceMapping;
//import org.springframework.data.rest.core.mapping.ResourceMappings;
//import org.springframework.data.rest.core.mapping.ResourceMetadata;
//import org.springframework.data.rest.core.mapping.SearchResourceMappings;
//import org.springframework.data.rest.webmvc.*;
//import org.springframework.data.rest.webmvc.support.DefaultedPageable;
//import org.springframework.data.util.ClassTypeInformation;
//import org.springframework.data.util.TypeInformation;
//import org.springframework.data.web.PagedResourcesAssembler;
//import org.springframework.hateoas.server.EntityLinks;
//import org.springframework.hateoas.server.core.AnnotationAttribute;
//import org.springframework.hateoas.server.core.MethodParameters;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.Assert;
//import org.springframework.util.ClassUtils;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.annotation.*;
//
//import com.g.commons.model.PagedModelLite;
//
//@BasePathAwareController
//public class CustomSearchController {
//    private static final String SEARCH = "/search";
//    private static final String BASE_MAPPING = CustomRestController.CUSTOM_REST_ROOT_MAPPING + "/{repository}" + SEARCH;
//
//    private final ResourceMappings mappings;
//    private ResourceStatus resourceStatus;
//
//    /**
//     * Creates a new {@link CustomSearchController} using the given {@link PagedResourcesAssembler},
//     * {@link EntityLinks} and {@link ResourceMappings}.
//     *
//     * @param assembler must not be {@literal null}.
//     * @param mappings  must not be {@literal null}.
//     */
//    @Autowired
//    public CustomSearchController(PagedResourcesAssembler<Object> assembler,
//                                  ResourceMappings mappings, HttpHeadersPreparer headersPreparer) {
//
//        Assert.notNull(mappings, "ResourceMappings must not be null!");
//
//        this.mappings = mappings;
//        this.resourceStatus = ResourceStatus.of(headersPreparer);
//    }
//
//    /**
//     * Executes the search with the given name.
//     *
//     * @param resourceInformation
//     * @param parameters
//     * @param search
//     * @param pageable
//     * @param sort
//     * @param assembler
//     * @return
//     * @throws ResourceNotFoundException
//     */
//    @ResponseBody
//    @RequestMapping(value = BASE_MAPPING + "/{search}", method = RequestMethod.GET)
//    public ResponseEntity<?> executeSearch(RootResourceInformation resourceInformation,
//                                           @RequestParam MultiValueMap<String, Object> parameters,
//                                           @PathVariable String search,
//                                           DefaultedPageable pageable,
//                                           Sort sort,
//                                           PersistentEntityResourceAssembler assembler,
//                                           @RequestHeader HttpHeaders headers) {
//
//        Method method = checkExecutability(resourceInformation, search);
//        Optional<Object> result = executeQueryMethod(resourceInformation.getInvoker(), parameters, method, pageable, sort,
//                assembler);
//
//        SearchResourceMappings searchMappings = resourceInformation.getSearchMappings();
//        MethodResourceMapping methodMapping = searchMappings.getExportedMethodMappingForPath(search);
//        Class<?> domainType = methodMapping.getReturnedDomainType();
//
//        return toModel(result, assembler, domainType, headers, resourceInformation);
//    }
//
//    /**
//     * @param source     can be must not be {@literal null}.
//     * @param assembler  must not be {@literal null}.
//     * @param domainType the domain type in case the source is an empty iterable, must not be {@literal null}.
//     * @return
//     */
//    protected ResponseEntity<?> toModel(Optional<Object> source,
//                                        final PersistentEntityResourceAssembler assembler,
//                                        Class<?> domainType,
//                                        HttpHeaders headers,
//                                        RootResourceInformation information) {
//
//        return source.map(it -> {
//
//            if (it instanceof PageImpl<?>) {
//                return ResponseEntity.ok(new PagedModelLite<>((PageImpl) it, oneIndexedParameters));
//            } else if (it instanceof Iterable) {
//                return ResponseEntity.ok(it);
//            } else if (ClassUtils.isPrimitiveOrWrapper(it.getClass())) {
//                return ResponseEntity.ok(it);
//            }
//
//            PersistentEntity<?, ?> entity = information.getPersistentEntity();
//
//            // Returned value is not of the aggregates type - probably some projection
//            if (!entity.getType().isInstance(it)) {
//                return ResponseEntity.ok(it);
//            }
//
//            return resourceStatus.getStatusAndHeaders(headers, it, entity).toResponseEntity(it);
//
//        }).orElseThrow(() -> new ResourceNotFoundException());
//    }
//
//    /**
//     * Checks that the given request is actually executable. Will reject execution if we don't find a search with the
//     * given name.
//     *
//     * @param resourceInformation
//     * @param searchName
//     * @return
//     */
//    private Method checkExecutability(RootResourceInformation resourceInformation, String searchName) {
//
//        SearchResourceMappings searchMapping = verifySearchesExposed(resourceInformation);
//
//        Method method = searchMapping.getMappedMethod(searchName);
//
//        if (method == null) {
//            throw new ResourceNotFoundException();
//        }
//
//        return method;
//    }
//
//    private Optional<Object> executeQueryMethod(final RepositoryInvoker invoker,
//                                                @RequestParam MultiValueMap<String, Object> parameters,
//                                                Method method, DefaultedPageable pageable, Sort sort,
//                                                PersistentEntityResourceAssembler assembler) {
//
//        MultiValueMap<String, Object> result = new LinkedMultiValueMap<String, Object>(parameters);
//        MethodParameters methodParameters = new MethodParameters(method, new AnnotationAttribute(Param.class));
//        List<MethodParameter> parameterList = methodParameters.getParameters();
//        List<TypeInformation<?>> parameterTypeInformations = ClassTypeInformation.from(method.getDeclaringClass())
//                .getParameterTypes(method);
//
//        parameters.entrySet().forEach(entry ->
//
//                methodParameters.getParameter(entry.getKey()).ifPresent(parameter -> {
//
//                    int parameterIndex = parameterList.indexOf(parameter);
//                    TypeInformation<?> domainType = parameterTypeInformations.get(parameterIndex).getActualType();
//
//                    ResourceMetadata metadata = mappings.getMetadataFor(domainType.getType());
//
//                    if (metadata != null && metadata.isExported()) {
//                        result.put(parameter.getParameterName(), prepareUris(entry.getValue()));
//                    }
//                }));
//
//        return invoker.invokeQueryMethod(method, result, pageable.getPageable(), sort);
//    }
//
//
//    /**
//     * Verifies that the given {@link RootResourceInformation} has searches exposed.
//     *
//     * @param resourceInformation
//     */
//    private static SearchResourceMappings verifySearchesExposed(RootResourceInformation resourceInformation) {
//
//        SearchResourceMappings resourceMappings = resourceInformation.getSearchMappings();
//
//        if (!resourceMappings.isExported()) {
//            throw new ResourceNotFoundException();
//        }
//
//        return resourceMappings;
//    }
//
//    /**
//     * Tries to turn all elements of the given {@link List} into URIs and falls back to keeping the original element if
//     * the conversion fails.
//     *
//     * @param source can be {@literal null}.
//     * @return
//     */
//    private static List<Object> prepareUris(List<Object> source) {
//
//        if (source == null || source.isEmpty()) {
//            return Collections.emptyList();
//        }
//
//        List<Object> result = new ArrayList<Object>(source.size());
//
//        for (Object element : source) {
//
//            try {
//                result.add(new URI(element.toString()));
//            } catch (URISyntaxException o_O) {
//                result.add(element);
//            }
//        }
//
//        return result;
//    }
//}
