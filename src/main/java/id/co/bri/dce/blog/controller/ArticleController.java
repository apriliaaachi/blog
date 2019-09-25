package id.co.bri.dce.blog.controller;

import id.co.bri.dce.blog.entity.Article;
import id.co.bri.dce.blog.entity.Comment;
import id.co.bri.dce.blog.repository.ArticleDao;
import id.co.bri.dce.blog.repository.CommentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;

@Controller
public class ArticleController {
    @Autowired
    private ArticleDao articleDao;
    private CommentDao commentDao;

    @GetMapping("/new_article")
    public String newArticle(Model model) {
        model.addAttribute("article", new Article());
        return "create_article";
    }

    @PostMapping("/save_article")
    public String saveArticle(@ModelAttribute Article article) {
        Date date = new Date();
        article.setDate(date);
        article.setAbout("lalalala");
        article.setAuthor("Achi Aprilia A");
        article.setStatus("ACTIVE");
        articleDao.save(article);
        return "redirect:/detail_article";
    }

    @GetMapping("/read_more/{id}")
    public String readMore(@PathVariable("id") long id, Model model) {
        Article article = articleDao.findById(id);
        Comment comment = commentDao.findByArticleId(article.getId());
        model.addAttribute("article", article);
        model.addAttribute("comment", comment);
        return "detail_article";
    }

    @PostMapping("/post_comment")
    public String comment(@ModelAttribute Comment comment, @ModelAttribute Article article) {
        comment.setId(article.getId());
        commentDao.save(comment);
        return "detail_article";
    }
}
