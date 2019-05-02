package com.bcp.mdp.service;

import com.bcp.mdp.dao.ExchangeDao;
import com.bcp.mdp.dto.ValorisationRequest;
import com.bcp.mdp.dto.ValorisationResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class ExchangeService implements IExchangeService {
    @Autowired
    private ExchangeDao exchangeDao;

    @Autowired
    private IAccountService accountService;

    @Override
    public void createExchange() {

    }

    @Override
    public void updateExchange() {

    }

    @Override
    public void deleteExchange() {

    }

    @Override
    public void retrieveExchangeRate() {

    }

    @Override
    public void retrieveExchangesCurrency() {

    }

    @Override
    public ValorisationResponse valoriseCommission(ValorisationRequest valorisationRequest) 
    {

    	String currencyDebit=accountService.retrieveAccountCurrency(valorisationRequest.getAccountDebit());
    	ValorisationResponse valorisationResponse=new ValorisationResponse();
    	// pour valoriser la commission est ce qu'on utilise le cours instantané ou bien un cours spécifiques
    	valorisationResponse.setExchangeTransferCurrencyToMAD( convertCurrencyAmount(valorisationRequest.getTransactionCurrency(),valorisationRequest.getCommissionAmount()));
    	
    	if(currencyDebit.equals(valorisationRequest.getTransactionCurrency()))
    	{
    		valorisationResponse.setExchangeTransferCurrencyToOther(valorisation(valorisationRequest.getAccountDebit()
    																, valorisationRequest.getAmount()
    																, valorisationRequest.getTransactionCurrency()
    																,""));//il faut forcement une autre devise
    		
    	}
    	else
    	{
    		valorisationResponse.setExchangeTransferCurrencyToOther(valorisation(valorisationRequest.getAccountDebit()
					, valorisationRequest.getAmount()
					, valorisationRequest.getTransactionCurrency()
					, currencyDebit));
    	}
    	
    	return valorisationResponse;
    }

    // cette fonction est utilisée pour convertir le seuil  du  dirahm vers de la devise de transaction 
    @Override
    public double convertCurrencyAmount(String transactionCurrency,  double amount) {
        return exchangeDao.getInstantCourseRate("MAD",transactionCurrency)*amount;
    }

    @Override
    public  double valorisation(long accountDebit, double amount, String transactionCurrency, String to) {
    	// le taux d'échange est en fonction de la categorie du donner d'ordre qui est le débilteur
        String customerCategory=accountService.getCustomerCategoryByAccountId(accountDebit);
        
        

        // Pour les clients particuliers
        if(customerCategory.equals("particulierSimple") || customerCategory.equals("particulierProfessionel")){
        	
        	// le seuil doit etre converti en la dévise de la transaction pour le comparer avec le montant.
            double s1 = convertCurrencyAmount(transactionCurrency,1000);
            double s2 = convertCurrencyAmount(transactionCurrency,2000);
            
            // Si le montant de l’opération>= seuil S2 paramétré alors application du cours négocié individuellement
            if(amount>=s2){
                return exchangeDao.getIndividualNegociatedCourseRate(transactionCurrency,to);
            }
            // Si le montant de l’opération>= seuil S1 et < Seuil S2 paramétré alors application du cours négocié par lot
            else if(amount>=s1 && amount<s2){
                return  exchangeDao.getBatchNegociatedCourseRate(transactionCurrency,to);
            }
            // Si le montant de l’opération< seuil paramétré alors application du cours instantané d’une manière automatique.
            else {
                return exchangeDao.getInstantCourseRate(transactionCurrency,to);
            }
        }

        // Pour les clients Entreprises
        if(accountService.retrieveAccountCustomer(accountDebit).getClass().getName().equals("CustomerEntreprise"))
        {
        // Si le client est un client de la salle de marché alors :Application du cours négocié individuellement
        	if(customerCategory.equals("client salle de marché"))
        	{
	        	// verifier d'abord le délai du ticket de negociation , si delai non depassée
	        	return exchangeDao.getIndividualNegociatedCourseRate(transactionCurrency,to);
        	}
        	// Sinon, Application du cours négocié par lot.
            return exchangeDao.getBatchNegociatedCourseRate(transactionCurrency,to);
        }

        // Pour le personnel de la banque 
        if(customerCategory.equals("particulierPersonnel")) 
        {
        	return exchangeDao.getPreferentialCourseRate(transactionCurrency, to);
        }


        return exchangeDao.getInstantCourseRate(transactionCurrency,to);
    }
}
