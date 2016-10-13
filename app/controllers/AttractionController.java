package controllers;

import data.Attraction;
import play.mvc.Controller;
import play.mvc.Result;
import java.util.List;

import static play.libs.Json.toJson;

public class AttractionController extends Controller {

    public Result getAttractions(){

        //Attraction attraction = new Attraction(0, "Ice Skating Park", "Aguadilla", "Awesome place to ice skate!", "img/iceSkating.jpg");
        //List<String> attractions = new List<>();
        //attractions.add(attraction.toString());

        return ok("OK!");

    }

}
