package com.bcp.mdp.web.controller;

import com.bcp.mdp.dto.CommissionDto;
import com.bcp.mdp.dto.TarificationOfTransaction;

import com.bcp.mdp.model.TransferType;
import com.bcp.mdp.service.IExchangeService;
import com.bcp.mdp.service.ITarificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin/*(origins = "http://localhost:3000", maxAge = 3600)*/
@RestController
@RequestMapping("/api/tarification")
public class TarificationController {
	@Autowired
    ITarificationService tarificationService;

	@Autowired
	IExchangeService exchangeService;

	@PostMapping("/tarifier")
	public TarificationOfTransaction tarif(@RequestBody CommissionDto commission) {
		/*reception d'un tableau dont:
		 * le premier element est le compte à debiter 
		 * le deuxième element est le compte à crediter
		 * le troisième  element est le montant de la transaction
		 */
		//System.out.println(exchangeService.convertCurrencyAmount("MAD","USD",100));
		//exchangeService.valorisation(2728,123,"MAD","USD");
		return tarificationService.retrieveTarification(commission.getAccountN1(), commission.getAccountN2(),commission.getAmount());
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
