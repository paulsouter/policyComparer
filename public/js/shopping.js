/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


"use strict";

var module = angular.module('ShoppingApp', ['ngResource', 'ngStorage']);

module.factory('policyDAO', function ($resource) {
    return $resource('/api/policies/:id');
});
module.factory('partyDAO', function ($resource) {
    return $resource('/api/parties/:id');
});


module.controller('PartyController', function (partyDAO) {
    this.parties = partyDAO.query();
    this.getPartys = function () {
        this.parties = partyDAO.query();
    };
      this.seeParty= function (party) {
        $sessionStorage.party = party;
        $window.location.href = './partyview.html';
        this.selectedParty = $sessionStorage.party;
    };
    this.getParty= function(){
        return this.selectedParty
    };


});

module.controller('PolicytController', function (policyDAO, tagsDAO, $sessionStorage, $window) {
    this.policies = policyDAO.query();
    // load the categories
    this.tags = tagsDAO.query();
    // click handler for the category filter buttons
    this.selectedPolicy= $sessionStorage.policy;
    this.getPolicys = function () {
        this.policys = policyDAO.query();
    };

    this.selectTags = function (selectedtag) {
        this.policys = tagsDAO.query({"tag": selectedtag});

    };
    this.seePolicy = function (policy) {
        $sessionStorage.policy = policy;
        $window.location.href = './policyView.html';
        this.selectedPolicy = $sessionStorage.policy;
    };
    this.getPolicy = function(){
        return this.selectedPolicy;
    };

});

module.factory('tagsDAO', function ($resource) {
    return $resource('/api/tags/:tag');
});

