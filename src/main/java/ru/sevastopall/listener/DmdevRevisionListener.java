package ru.sevastopall.listener;

import org.hibernate.envers.RevisionListener;
import ru.sevastopall.entity.Revision;

public class DmdevRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {

        ((Revision) revisionEntity).setUsername("vsevolod");
    }

}
