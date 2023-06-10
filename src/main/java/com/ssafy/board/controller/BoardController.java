package com.ssafy.board.controller;

import com.ssafy.user.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssafy.board.dto.Board;
import com.ssafy.board.service.BoardService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;

@ApiIgnore
@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {
	// Vue에서는 controller는 필요 없어지니.. 수정 안 할 예정

	@Autowired
	BoardService boardService;
	
	@GetMapping("/{name}")
	public String boardPage(@PathVariable String name, Model model, HttpSession session) {
		model.addAttribute("name", name);

		boolean adminReq = boardService.adminRequired(name);
		if(adminReq){
			model.addAttribute("adminReq", true);

			Object isAdmin = session.getAttribute("admin");
			if(isAdmin != null && (boolean) isAdmin){
				model.addAttribute("admin", true);
			}

		}

		return "board";
	}
	
	@GetMapping("/{name}/{no}")
	public String boardDetail(@PathVariable String name, @PathVariable int no, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
		Board board = boardService.select(name, no);

		if(board == null){
			redirectAttributes.addFlashAttribute("msg", "존재하지 않는 글입니다.");
			return "redirect:/";
		}

		model.addAttribute("article", board);
		model.addAttribute("name", name);

		Object isAdmin = session.getAttribute("admin");
		log.debug("isAdmin : {}", isAdmin);
		User user = (User) session.getAttribute("user");
		log.debug("User : {}", user);
		log.debug("Board : {}", board);

		if(isAdmin != null || (user != null && (user.getNo() == board.getAuthorNo()))){
			model.addAttribute("editable", true);
		}

		return "detail";
	}

	@GetMapping("/{name}/write")
	public String boardWrite(@PathVariable String name, Model model, HttpSession session, RedirectAttributes redirectAttributes){
		boolean adminReq = boardService.adminRequired(name);
		if(adminReq){
			Object isAdmin = session.getAttribute("admin");
			if(isAdmin == null){
				redirectAttributes.addFlashAttribute("msg", "비정상적인 접근입니다.");
				return "redirect:/";
			}
		}
		model.addAttribute("name", name);

		return "write";
	}

	@GetMapping("/{name}/{no}/edit")
	public String boardModify(@PathVariable String name, @PathVariable int no, Model model, HttpSession session, RedirectAttributes redirectAttributes){
		Board board = boardService.select(name, no);

		if(board == null){
			redirectAttributes.addFlashAttribute("msg", "존재하지 않는 글입니다.");
			return "redirect:/";
		}

		User user = (User) session.getAttribute("user");

		if(user == null || user.getNo() != board.getAuthorNo()){
			redirectAttributes.addFlashAttribute("msg", "유효하지 않은 접근입니다.");
		}

		model.addAttribute("name", name);
		model.addAttribute("data", board);


		return "edit";
	}
}
