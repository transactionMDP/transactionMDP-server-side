package com.bcp.mdp.service;

import com.bcp.mdp.dao.ExchangeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
    public void valorizeCurrency() {

    }

    @Override
    public double convertCurrencyAmount(String from, String to, double amount) {
        return exchangeDao.getInstantCourseRate(from,to)*amount;
    }

    @Override
    public double valorisation(long accountDebit, double amount, String from, String to) {
        String customerCategory=accountService.getCustomerCategoryByAccountId(accountDebit);
        double s1 = 1000;
        double s2 = 2000;

        // Pour les clients particuliers
        if(customerCategory.equals("particulierSimple") || customerCategory.equals("particulierProfession")){
            // Si le montant de l’opération>= seuil S2 paramétré alors application du cours négocié individuellement
            if(amount>=s2){
                return exchangeDao.getIndividualNegociatedCourseRate(from,to);
            }
            // Si le montant de l’opération>= seuil S1 et < Seuil S2 paramétré alors application du cours négocié par lot
            else if(amount>=s1 && amount<s2){
                return  exchangeDao.getBatchNegociatedCourseRate(from,to);
            }
            // Si le montant de l’opération< seuil paramétré alors application du cours instantané d’une manière automatique.
            else {
                return exchangeDao.getInstantCourseRate(from,to);
            }
        }

        // Pour les clients Entreprises
        // Si le client est un client de la salle de marché alors :Application du cours négocié individuellement
        else if(customerCategory.equals("client salle de marché")){
            return exchangeDao.getInstantCourseRate(from,to);
        }
        // Sinon, Application du cours négocié par lot.
        else if(customerCategory.equals("TPE")){
            return exchangeDao.getBatchNegociatedCourseRate(from,to);
        }

        // Pour le personnel de la banque


        // Sinon
        else {
            return exchangeDao.getInstantCourseRate(from,to);
        }
    }
}
