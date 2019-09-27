package id.co.bri.dce.blog.controller;

import id.co.bri.dce.blog.entity.Article;
import id.co.bri.dce.blog.entity.Comment;
import id.co.bri.dce.blog.entity.User;
import id.co.bri.dce.blog.repository.ArticleDao;
import id.co.bri.dce.blog.repository.CommentDao;
import org.aspectj.weaver.patterns.HasMemberTypePatternForPerThisMatching;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class ArticleController {
    @Autowired
    private ArticleDao articleDao;
    private CommentDao commentDao;

    @GetMapping("/new_article")
    public String newArticle(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("article", new Article());
        return "create_article";
    }

    @PostMapping("/save_article")
    public String saveArticle(@ModelAttribute Article article, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Date date = new Date();
        article.setDate(date);
        article.setUser(user);
        article.setStatus("ACTIVE");
        articleDao.save(article);
        return "home";
    }

    @GetMapping("/detail_article/{id}")
    public String readMore(@PathVariable("id") long id, Model model, HttpSession session) {
        Article article = articleDao.findById(id);
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("comment", new Comment());
        model.addAttribute("article", article);
        return "detail_article";
    }

    @PostMapping("/post_comment")
    public String comment(@ModelAttribute Comment comment, @ModelAttribute Article article) {
        comment.setArticle(article);
        commentDao.save(comment);
        return "detail_article";
    }

    @GetMapping("/edit_article/{id}")
    public String editArticle(@PathVariable("id") long id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Article article = articleDao.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("article", article);
        return "edit_article";
    }

    @PostMapping("/update_article/{id}")
    public String updateArticle(@PathVariable long id, @Valid Article article) {
        article.setStatus("ACTIVE");
        articleDao.save(article);

        return "detail_article";
    }

    @RequestMapping(value = "/delete_article/{id}", method = RequestMethod.GET)
    public String deleteArticle(@PathVariable long id) {
        Article article = articleDao.findById(id);
        article.setStatus("DISABLED");
        articleDao.save(article);

        return "redirect:/homepage";
    }

    @GetMapping("/homepage")
    public ModelAndView homepage(@RequestParam("page") Optional<Integer> page,
                                 @RequestParam("size") Optional<Integer> size, HttpSession session) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(3);

        User user = (User) session.getAttribute("user");

        Pageable paging = PageRequest.of(currentPage - 1, pageSize, Sort.by("date"));
        ModelAndView mv = new ModelAndView("home");

        mv.addObject("user", user);
        Page<Article> articles = articleDao.findByStatus("ACTIVE", paging);

        mv.addObject("page", articles);
        mv.addObject("articles", articles.getContent());
        return mv;
    }

}
