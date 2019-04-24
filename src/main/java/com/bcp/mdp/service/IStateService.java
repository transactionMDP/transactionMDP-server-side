package com.bcp.mdp.service;

import com.bcp.mdp.model.State;

public interface IStateService {

	State retrieveStateByLibelle(String libelle);

}
