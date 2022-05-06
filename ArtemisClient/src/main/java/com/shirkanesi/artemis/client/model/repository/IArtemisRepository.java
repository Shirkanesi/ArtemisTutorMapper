package com.shirkanesi.artemis.client.model.repository;

import com.shirkanesi.artemis.client.logic.ArtemisClient;

public interface IArtemisRepository {

    RepositoryType getRepositoryType();

    boolean lockRepository(ArtemisClient artemisClient);

    boolean unlockRepository(ArtemisClient artemisClient);

}
