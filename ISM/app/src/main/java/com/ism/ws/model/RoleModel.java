package com.ism.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
/**
 * Created by c162 on 19/11/15.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleModel {
	
    private ArrayList<RolemodelData> suggestedRolemodel;
    private ArrayList<RolemodelData> favoriteRolemodel;

    @JsonProperty("suggested_rolemodel")
    public ArrayList<RolemodelData> getSuggestedRolemodel() {
        return this.suggestedRolemodel;
    }

    public void setSuggestedRolemodel(ArrayList<RolemodelData> suggestedRolemodel) {
        this.suggestedRolemodel = suggestedRolemodel;
    }
    @JsonProperty("favorite_rolemodel")
    public ArrayList<RolemodelData> getFavoriteRolemodel() {
        return this.favoriteRolemodel;
    }

    public void setFavoriteRolemodel(ArrayList<RolemodelData> favoriteRolemodel) {
        this.favoriteRolemodel = favoriteRolemodel;
    }


    
}
