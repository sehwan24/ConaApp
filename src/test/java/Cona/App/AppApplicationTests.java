package Cona.App;

import Cona.App.domain.Answer;
import Cona.App.domain.Notification;
import Cona.App.repository.AnswerRepository;
import Cona.App.repository.NotificationRepository;
import Cona.App.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AppApplicationTests {

	@Autowired // 스프링이 객체 대신 생성, 순환참조 문제와 같은 이유로 테스트코드 외에는 생성자를 통한 객체 주입방식 권장. *순환참조 : A클래스가 B클래스의 Bean주입받고, B클래스가 A클래스의 Bean 주입받는 상황
	private NotificationRepository notificationRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Autowired
	private NotificationService notificationService;

	@Test
	void makeTest() {
//		테스트데이터 생성
		Notification n1 = new Notification();
		n1.setSubject("sbb가 무엇인가요?");
		n1.setContent("sbb에 대해서 알고 싶습니다.");
		n1.setCreateDate(LocalDateTime.now());
		this.notificationRepository.save(n1);  // 첫번째 질문 저장

		Notification n2 = new Notification();
		n2.setSubject("스프링부트 모델 질문입니다.");
		n2.setContent("id는 자동으로 생성되나요?");
		n2.setCreateDate(LocalDateTime.now());
		this.notificationRepository.save(n2);  // 두번째 질문 저장
	}
	@Test
	void findAllTest() {
//		데이터 수는 일치하는지, 데이터 내용 일치하는지
		List<Notification> all = this.notificationRepository.findAll();
		assertEquals(2, all.size());

		Notification n = all.get(0);
		assertEquals("sbb가 무엇인가요?", n.getSubject());
	}

	@Test
	void findByIdTest() {
		Optional<Notification> on = this.notificationRepository.findById(1);
		if (on.isPresent()) {
			Notification n = on.get();
			assertEquals("sbb가 무엇인가요?", n.getSubject());
		}
	}
	@Test
	void findBySubjectTest() {
		Notification n = this.notificationRepository.findBySubject("sbb가 무엇인가요?");
		assertEquals(1, n.getId());
	}
	@Test
	void findBySubjectAndContentTest() {
		Notification n = this.notificationRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
		assertEquals(1, n.getId());
	}
	@Test
	void findBySubjectLikeTest() {
		List<Notification> nList = this.notificationRepository.findBySubjectLike("sbb%");
		Notification n = nList.get(0);
		assertEquals("sbb가 무엇인가요?", n.getSubject());
	}

	@Test
	void updateSubjectTest() {
		Optional<Notification> on = this.notificationRepository.findById(1);
		assertTrue(on.isPresent());
		Notification n = on.get();
		n.setSubject("수정된 제목");
		this.notificationRepository.save(n);
	}
	@Test
	void deleteById() {
		assertEquals(2, this.notificationRepository.count());
		Optional<Notification> on = this.notificationRepository.findById(1);
		assertTrue(on.isPresent());
		Notification n = on.get();
		this.notificationRepository.delete(n);
		assertEquals(1, this.notificationRepository.count());
	}

	@Test
	void answerCreateTest() {
		Optional<Notification> on = this.notificationRepository.findById(2);
		assertTrue(on.isPresent());
		Notification n = on.get();
		Answer a = new Answer();
		a.setContent("네 자동으로 생성됩니다.");
		a.setNotification(n); //어떤 질문의 답변인지 알기 위해 Notification 객체 필요함.
		a.setCreateDate(LocalDateTime.now());
		this.answerRepository.save(a);
	}
	@Test
	void findByIdAns() {
		Optional<Answer> oa = this.answerRepository.findById(1);
		assertTrue(oa.isPresent());
		Answer a = oa.get();
		assertEquals(2, a.getNotification().getId());
	}

	@Transactional	//어노테이션 제거시 에러 발생. why) 첫 줄에서 findById로 객체 조회 후 DB세션 끊기므로 line5에서 q.getAnswerList 메서드 실행시 오류 발생. why)답변 데이터 리스트는 첫 객체 조회 시 가져오지 않음.
	@Test
	void findAnsByNot() {
		Optional<Notification> on = this.notificationRepository.findById(2);
		assertTrue(on.isPresent());
		Notification n = on.get();

		List<Answer> answerList = n.getAnswerList();

		assertEquals(1, answerList.size());
		assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
	}

	@Test
	void makeTestcase() {
		for (int i = 1; i <= 60; i++) {
			String subject = String.format("테스트 데이터입니다:[%03d]", i);
			String content = "CONA 차기 운영진 모집 공지";
			this.notificationService.create(subject, content, null);
		}
	}
}
