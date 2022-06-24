package go.cows.controller;

import go.cows.dao.UserDao;
import go.cows.model.User;
import go.cows.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsersController {

    @Autowired
    private UserService userService;

    /*Чтобы не мучитьчся с атрибутами - сразу на страницу кидаю
    нового пустого юзера, на случай создания ему полей и добавления в БД.
    Не уверен, что это норм */

    /*При редактировании юзера, на главную страницу летит редактируемый юзер*/

    @GetMapping()
    public String index(@RequestParam(value = "idUserToEdit", required = false) Long idUserToEdit,  Model model){
        model.addAttribute("users", userService.getAll());
        model.addAttribute("user", newUserOrUserToEdit(idUserToEdit)); // наверное, это зря...
        return "users/index";
    }

    // Получает id юзера для редактирования и перенаправляет на главную страницу
    // На ней форма для редактирования.
    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") Long id, Model model){
        model.addAttribute("idUserToEdit", id);
        return "redirect: /";
    }

    // Получил данные со страницы, сохранил юзера и сразу редирект на главную
    @PostMapping("/save")
    public String create(@ModelAttribute("user") User user){
        if(user.getName() != null && !user.getName().isEmpty()){ // todo: метод для валидации строки
            userService.save(user);
        }
        return "redirect: /";
    }

    // Вот хз. Вроде как нерекомендуется делать апдейт методом POST..
    // 2 одинаковыйх метода. У меня сомнения...

    // todo: возможно стоит объединить в один POST
    // Сохранение отредактированного юзера
    @PatchMapping("/save")
    public String edit(@ModelAttribute("user") User user){
        if(user.getName() != null && !user.getName().isEmpty()){ // todo: метод для валидации строки
            userService.save(user);
        }
        return "redirect: /";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        userService.delete(id);
        return "redirect: /";
    }

    private User newUserOrUserToEdit(Long id){
        return id == null ? new User() : userService.get(id);
    }
}
