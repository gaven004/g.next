package org.hibernate.tool.internal.export.entity;

import java.util.*;

import org.hibernate.mapping.MetaAttribute;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.tool.internal.export.java.JavaExporter;
import org.hibernate.tool.internal.export.java.MetaAttributeConstants;
import org.hibernate.tool.internal.export.java.POJOClass;

public class EntityExporter extends JavaExporter {
    private static final String REPOSITORY_FTL = "entity/Entity.ftl";

    private String sessionFactoryName = "SessionFactory";

    public EntityExporter() {
        super();
    }

    protected void init() {
        super.init();
        getProperties().put(TEMPLATE_NAME, REPOSITORY_FTL);
        getProperties().put(FILE_PATTERN, "{package-name}/{class-name}.java");
    }

    protected void exportComponent(Map<String, Object> additionalContext, POJOClass element) {
        // noop - we dont want components
    }

    protected void setupContext() {
        super.setupContext();
    }

    public String getName() {
        return "hbm2entity";
    }

    @Override
    protected void doStart() {
        String baseClass = getProperties().getProperty("baseClass");

        if (baseClass == null) {
            log.debug("No base class, use pojo exporter");
            super.doStart();
        } else {
            log.debug("No base class, use entity exporter");
            Iterator<?> iterator = getPOJOIterator(getMetadata().getEntityBindings().iterator(), baseClass);
            Map<String, Object> additionalContext = new HashMap<String, Object>();
            while (iterator.hasNext()) {
                POJOClass element = (POJOClass) iterator.next();
                exportPersistentClass(additionalContext, element);
            }
        }
    }

    private Iterator<?> getPOJOIterator(Iterator<PersistentClass> persistentClasses, String baseClass) {
        String shortName = baseClass.lastIndexOf('.') >= 0 ?
                baseClass.substring(baseClass.lastIndexOf('.') + 1) : baseClass;

        return new Iterator<POJOClass>() {
            public POJOClass next() {
                PersistentClass persistentClass = persistentClasses.next();

                Map attributes = new HashMap();
                if (persistentClass.getMetaAttributes() != null && !persistentClass.getMetaAttributes().isEmpty()) {
                    attributes.putAll(persistentClass.getMetaAttributes());
                }

                MetaAttribute attribute = new MetaAttribute(MetaAttributeConstants.EXTENDS);
                attribute.addValue(shortName);
                attributes.put(MetaAttributeConstants.EXTENDS, attribute);

                persistentClass.setMetaAttributes(attributes);

                POJOClass pojoClass = getCfg2JavaTool().getPOJOClass(persistentClass);
                pojoClass.importType(baseClass);
                return pojoClass;
            }

            public boolean hasNext() {
                return persistentClasses.hasNext();
            }

            public void remove() {
                persistentClasses.remove();
            }
        };
    }
}
