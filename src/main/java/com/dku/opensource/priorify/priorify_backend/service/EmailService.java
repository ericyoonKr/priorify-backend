package com.dku.opensource.priorify.priorify_backend.service;

import com.dku.opensource.priorify.priorify_backend.dto.ScheduleListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;



    public void sendScheduleReminderEmail(String to, String subject, List<ScheduleListDto> schedules, int daysRemaining) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom("your_email@example.com"); // application.properties의 username과 동일 또는 설정
        helper.setTo(to);
        helper.setSubject(subject);
        String htmlContent = buildScheduleHtmlContent(schedules, daysRemaining);
        helper.setText(htmlContent, true); // true는 HTML 이메일임을 나타냄

        mailSender.send(message);


    }
    /**
     * 스케줄 목록을 포함하는 HTML 이메일 본문 생성
     * @param schedules 정렬된 스케줄 목록
     * @param daysRemaining 남은 날짜
     * @return HTML 형식의 이메일 본문
     */
    private String buildScheduleHtmlContent(List<ScheduleListDto> schedules, int daysRemaining) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body>");
        sb.append("<h2>Priorify 스케줄 알림</h2>");

        String periodDescription;
        if (daysRemaining == 0) {
            periodDescription = "오늘 시작하는";
        } else if (daysRemaining == 1) {
            periodDescription = "내일 시작하는";
        } else {
            periodDescription = daysRemaining + "일 후 시작하는";
        }

        sb.append("<p>안녕하세요.</p>");
        sb.append("<p>").append(periodDescription).append(" 스케줄 목록입니다.</p>");
        sb.append("<ul style='list-style: none; padding: 0;'>");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (ScheduleListDto schedule : schedules) {
            sb.append("<li style='border: 1px solid #ccc; margin-bottom: 10px; padding: 10px; border-radius: 5px;'>");
            sb.append("<strong>제목:</strong> ").append(schedule.getTitle()).append("<br>");
            if (schedule.getStartDate() != null) {
                sb.append("<strong>시작:</strong> ").append(schedule.getStartDate().format(formatter)).append("<br>");
            }
            if (schedule.getEndDate() != null) {
                sb.append("<strong>종료:</strong> ").append(schedule.getEndDate().format(formatter)).append("<br>");
            }
            if (schedule.getCategories() != null && !schedule.getCategories().isEmpty()) {
                sb.append("<strong>카테고리:</strong> ").append(String.join(", ", schedule.getCategories())).append("<br>");
            }
            if (schedule.getPriority() != null) {
                sb.append("<strong>우선 순위 점수:</strong> ").append(String.format("%.2f", schedule.getPriority())).append("<br>");
            }
            sb.append("</li>");
        }

        sb.append("</ul>");
        sb.append("<p>Priorify와 함께 효율적인 시간 관리를 하세요!</p>");
        sb.append("</body></html>");

        return sb.toString();
    }
}
