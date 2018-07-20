package controllers;

import db.DBHelper;
import db.helpers.DBTag;
import models.Article;
import models.Tag;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class TagController {

    public TagController(){
        this.setupEndPoints();
    }

    public void setupEndPoints(){

        get("/admin/tags", (req, res) -> {
            Map<String, Object> model = new HashMap();
            model.put("template", "templates/admin/tagTemplates/index.vtl");

            List<Tag> tags = DBTag.getAll();
            model.put("tags", tags);

            Map<Integer, List<Article>> tagArticles = DBTag.getMapOfArticlesForTags();
            model.put("tagArticles", tagArticles);

            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        post ("/tags/:id/delete", (req, res) -> {

            int tagId = Integer.parseInt(req.params(":id"));

            Tag tag = DBTag.find(tagId);

            DBHelper.delete(tag);

            res.redirect("/tags");
            return null;
        }, new VelocityTemplateEngine());


    }
}
