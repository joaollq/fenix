package net.sourceforge.fenixedu.presentationTier.renderers.factories;

import java.util.Collection;
import java.util.List;

import net.sourceforge.fenixedu.applicationTier.Servico.renderers.UpdateObjects.ObjectChange;
import net.sourceforge.fenixedu.renderers.model.CreationMetaObject;
import net.sourceforge.fenixedu.renderers.model.MetaObjectKey;

public class CreationDomainMetaObject extends DomainMetaObject implements CreationMetaObject {

    private Class type;
    private transient Object createdObject;

    public CreationDomainMetaObject(Class type) {
        super();

        this.type = type;
        setService("CreateObjects");
    }

    @Override
    public Object getObject() {
        return getType();
    }
    
    public Object getCreatedObject() {
        return this.createdObject;
    }

    @Override
    public int getOid() {
        return 0;
    }
    
    @Override
    public Class getType() {
        return type;
    }

    public MetaObjectKey getKey() {
        return new CreationMetaObjectKey(getType());
    }

    @Override
    protected Object callService(List<ObjectChange> changes) {
        Collection created = (Collection) super.callService(changes);
        
        // heuristic, does not work so well if multiple objects are created
        for (Object object : created) {
            if (getType().isAssignableFrom(object.getClass())) {
                this.createdObject = object;
                break;
            }
        }
        
        return created;
    }
}
