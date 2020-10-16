/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


"use strict";

var module = angular.module('ShoppingApp', ['ngResource', 'ngStorage']);

module.factory('policyDAO', function ($resource) {
    return $resource('/api/policy/:id');
});
module.factory('partyDAO', function ($resource) {
    return $resource('/api/party/:id');
});


module.controller('partyControler', function (partyDAO) {
    this.party = partyDAO.query();
    this.getPartys = function () {
        this.party = partyDAO.query();
    }

});

module.controller('PolicytController', function (policyDAO, tagsDAO) {
    this.policy = policyDAO.query();
    // load the categories
    this.tags = tagsDAO.query();
    // click handler for the category filter buttons
    this.getPolicys = function () {
        this.policy = policyDAO.query();
    };

    this.selectTags = function (selectedtag) {
        this.policy = tagsDAO.query({"tag": selectedtag});

    };

});

module.factory('tagsDAO', function ($resource) {
    return $resource('/api/tags/:tag');
});

