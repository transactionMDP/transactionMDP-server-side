package com.bcp.mdp.web.controller;

import com.bcp.mdp.dto.TarificationOfTransaction;
import com.bcp.mdp.model.Commission;
import com.bcp.mdp.model.TransferType;
import com.bcp.mdp.service.ITarificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin/*(origins = "http://localhost:3000", maxAge = 3600)*/
@RestController
@RequestMapping("/api/tarification")
public class TarificationController {
	@Autowired
    ITarificationService tarificationService;

	@PostMapping("/tarifier")
	public TarificationOfTransaction tarif(@RequestBody Commission commission) {
		/*reception d'un tableau dont:
		 * le premier element est le compte à debiter 
		 * le deuxième element est le compte à crediter
		 * le troisième  element est le montant de la transaction
		 */
		return null;//tarificationService.retrieveTarification(commission.getAccountN1(), commission.getAccountN2(),commission.getAmount());
	}

	@PostMapping("/transfertType")
	public TransferType verif (@RequestBody long[] accounts) {
		/*reception d'un tableau dont:
		 * le premier element est le compte à debiter 
		 * le deuxième element est le compte à crediter
		 */
		return tarificationService.verifyTransferType(accounts[0], accounts[1]);
		
	}

}
