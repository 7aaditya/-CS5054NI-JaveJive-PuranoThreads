package com.puranothread.controller;

import java.io.IOException;

import com.puranothread.service.FAQsPageService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(asyncSupported = true, urlPatterns = { "/FAQsPage" })
public class faqsontroller extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private FAQsPageService  fAQsPageService;

    public faqsontroller() {
        this.setFAQsPageService(new FAQsPageService());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/Pages/FaqsPage.jsp").forward(request, response);
        }

	public FAQsPageService getFAQsPageService() {
		return fAQsPageService;
	}

	public void setFAQsPageService(FAQsPageService fAQsPageService) {
		this.fAQsPageService = fAQsPageService;
	}
}

