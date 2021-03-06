package com.ksy.service.plan.test;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.Model;

import com.ksy.common.util.Util;
import com.ksy.service.domain.City;
import com.ksy.service.domain.Daily;
import com.ksy.service.domain.Day;
import com.ksy.service.domain.Memo;
import com.ksy.service.domain.Offer;
import com.ksy.service.domain.Party;
import com.ksy.service.domain.Plan;
import com.ksy.service.domain.Stuff;
import com.ksy.service.domain.Todo;
import com.ksy.service.domain.User;
import com.ksy.service.plan.PlanService;
import com.ksy.service.planSub.PlanSubService;

/*
 *	FileName :  PlanServiceTest.java
 * し JUnit4 (Test Framework) 引 Spring Framework 搭杯 Test( Unit Test)
 * し Spring 精 JUnit 4研 是廃 走据 適掘什研 搭背 什覗元 奄鋼 搭杯 砺什闘 坪球研 拙失 拝 呪 赤陥.
 * し @RunWith : Meta-data 研 搭廃 wiring(持失,DI) 拝 梓端 姥薄端 走舛
 * し @ContextConfiguration : Meta-data location 走舛
 * し @Test : 砺什闘 叔楳 社什 走舛
 */
@RunWith(SpringJUnit4ClassRunner.class)

//==> Meta-Data 研 陥丞馬惟 Wiring 馬切...
//@ContextConfiguration(locations = { "classpath:config/context-*.xml" })
@ContextConfiguration	(locations = {	"classpath:config/context-common.xml",
																	"classpath:config/context-aspect.xml",
																	"classpath:config/context-mybatis.xml",
																	"classpath:config/context-transaction.xml" })

public class PlanServiceTest {

	//==>@RunWith,@ContextConfiguration 戚遂 Wiring, Test 拝 instance DI
	@Autowired
	@Qualifier("planServiceImpl")
	private PlanService planService;
	
	/* Plan Controller 砺什闘遂 人戚嬢元 */
	@Autowired
	@Qualifier("planSubServiceImpl")
	private PlanSubService planSubService;

	
	@Test
	public void testGetPlanListByUserId() throws Exception {
		
		String userId = "admin";
		
		List<Plan> planList = planService.getPlanList(userId);
		
		System.out.println("\n\n\n	list size : "+planList.size());
		System.out.println("\n\n\n	planList :: "+planList);
	}
	
	//@Test
	public void testGetPlanByPlanId() throws Exception {
		
		String planId = "10030";
		Plan plan = planService.getPlan(planId);
		
		System.out.println("\n\n	plan : "+plan);
		
		planId = "10002";
		plan = planService.getPlan(planId);
		
		System.out.println("\n\n	plan : "+plan);	
	}
	
	
	//******** 		Controller 砺什闘		  ********//
	// ===>>> PlanControllerTest.java 稽 薪移姶ぞぞぞぞぞぞぞ
	//@Test
	public void testGetPlanController() throws Exception {
		Model model = new Model() {
			
			@Override
			public Model mergeAttributes(Map<String, ?> arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean containsAttribute(String arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Map<String, Object> asMap() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Model addAttribute(String arg0, Object arg1) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Model addAttribute(Object arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Model addAllAttributes(Map<String, ?> arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Model addAllAttributes(Collection<?> arg0) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		String planId = "10000";
		
		
		Plan plan = planService.getPlan(planId);
		//plan_id, plan_title, plan_img, plan_type, start_date, plan_status, (cr.sum - (cr.cnt-1)) plan_total_days, pt.plan_party_size 
		
		List<User> planPartyList = planService.getPlanPartyList(planId);	//planPartyList
		plan.setPlanPartyList(planPartyList);
		
		List<Todo> todoList = planService.getTodoList(planId); 				//todoList
		plan.setTodoList(todoList);
		
	
		//List<Daily> dailyList = planSubService.getDailyList(planId);		//dailyList
		List<Stuff> stuffList = planSubService.getStuffList(planId);		//stuffList
		List<Memo> memoList = planSubService.getMemoList(planId);			//memoList
		//plan.setDailyList(dailyList);
		plan.setStuffList(stuffList);
		plan.setMemoList(memoList);
		
		List<City> listCity = planSubService.getCityRouteList(planId);	
		/* */plan.setCityList(listCity);
		model.addAttribute("listCity", listCity);	//Plan 琶球拭 CityList 蒸嬢辞 乞季拭 宿嬢捜... :: 蓄板 痕井?
		
		//List<Day> dayList = Util.cityListToDayList(listCity);
		//lan.setDayList(dayList);
		
		plan.setPlanDday( Util.getDday(plan.getStartDate()));		//食楳 D-Day
		plan.setEndDate( Util.getEndDate(plan.getStartDate(), plan.getPlanTotalDays()) );	//食楳曽戟析切
		
		
		//獄憎 推鉦聖 是廃 軒什闘 : model拭 宿嬢辞 左鎧爽奄
		//List<Daily> budgetOverviewList = planSubService.getBudgetOverview(planId);
		/* *///plan.setBudgetOverviewList(budgetOverviewList);
		//model.addAttribute("budgetOverviewList", budgetOverviewList);
		
		model.addAttribute("plan", plan);
		
		System.out.println("\n\n- - - - - - - - - - - - plan - - - - - - - - - - \n");
		System.out.println(plan);
		
		String returnString = "forward:/plan/getPlan.jsp";
	}
	
	
	
	//@Test
	public void testGetTodoListByPlanId() throws Exception {
		
		String planId = "10000";
		List<Todo> todoList = planService.getTodoList(planId);
		
		System.out.println("\n\n	todoList : "+todoList);
	}
	
	//@Test
	public void testGetPlanPartyListByPlanId() throws Exception {
		
		String planId = "10000";
		List<User> planPartyList = planService.getPlanPartyList(planId);
		
		System.out.println("\n\n	planPartyList : "+planPartyList);
	}
	
	//@Test
	public void testAddPlan() throws Exception {	//失因!
		
		Plan plan = new Plan();
		User planMaster = new User();
		planMaster.setUserId("test2222");
		plan.setPlanMaster(planMaster);
		plan.setPlanTitle("砺什闘巴沓22");
		plan.setPlanImg("gooood.jpg");
		plan.setPlanType("D");
		plan.setStartDateString("20201212");	//走舛廃 杉戚 採旋杯杯艦陥 神嫌 -> 古遁拭辞 TO_DATE 竺舛背操辞 背衣
		
		planService.addPlan(plan);
	}
	
	
	//@Test
	public void testUpdatePlan() throws Exception {		//失因!
		
		
		Plan plan = planService.getPlan("10030");
		
		System.out.println("	before plan :: "+plan);
		
		plan = new Plan();
	
		plan.setPlanId("10030");
		plan.setPlanTitle("updatePlanTest");
		plan.setPlanImg("updateComplete.jpg");
		plan.setPlanType("C");
		plan.setStartDateString("20201212");	
		
		planService.updatePlan(plan);
		
		plan = new Plan();
		plan = planService.getPlan("10030");
		
		System.out.println("	after plan :: "+plan);	
	}
	
	//@Test
	public void testUpdatePlanStatus() throws Exception {		//失因!
		
		Plan plan = planService.getPlan("10030");
		System.out.println("	before plan :: "+plan);
		
		plan = new Plan();
		plan.setPlanId("10030");
		plan.setPlanStatus("C");
		
		planService.updatePlanStatus(plan);
		
		plan = planService.getPlan("10030");
		System.out.println("	after plan :: "+plan);	
	}
	
	//@Test
	public void testDeletePlan() throws Exception {		//失因!
		
		String userId = "admin";
		List<Plan> planList = planService.getPlanList(userId);
		System.out.println("\n\n\n	before planList :: "+planList);
		
		planService.deletePlan("10001");
		
		planList = planService.getPlanList(userId);
		System.out.println("\n\n\n	after planList :: "+planList);
	}
	
	//@Test
	public void testDeletePlanParty() throws Exception {		//失因!
		
		String planId = "10000";
		List<User> planPartyList = planService.getPlanPartyList(planId);
		System.out.println("\n\n	planPartyList : "+planPartyList);
				
		Party party = new Party();
		party.setPartyUserId("testMember");
		party.setRefId("10000");
		
		planService.deletePlanParty(party);
		
		planPartyList = planService.getPlanPartyList(planId);
		System.out.println("\n\n	planPartyList : "+planPartyList);
	}
	
	//@Test
	public void testCheckUserFromParty() throws Exception {		//失因!
		
		Party party = new Party();
		party.setPartyUserId("user01");
		party.setRefId("10000");
		
		String userId = planService.checkUserFromParty(party);
		if( userId != null ) {
			System.out.println(userId + " 還精 戚耕 巴掘格拭 凧食掻昔 呉獄脊艦陥");
		}else {
			System.out.println("user01" +"還聖 段企拝猿推?");
		}
		
		party.setPartyUserId("testMember");
		
		String userId2 = planService.checkUserFromParty(party);
		if( userId2 != null ) {
			System.out.println(userId2 + " 還精 戚耕 巴掘格拭 凧食掻昔 呉獄脊艦陥");
		}else {
			System.out.println("testMember" +" 還聖 段企拝猿推?");
		}
	}
	
	//@Test
	public void testAddOffer() throws Exception {		//失因!
		
		Offer offer = new Offer();
		offer.setRefId("10000");
		offer.setFromUserId("admin");
		offer.setToUserId("sysy");
		offer.setOfferMsg("五獣走 左鎧奄~~");
		
		planService.addOffer(offer);
	}
	
	//@Test
	public void testCheckTodo() throws Exception {		//失因!
		
		Todo todo = new Todo();
		todo.setTodoId("10023");
		todo.setTodoCheck("T");
		
		planService.checkTodo(todo);
	}
	
	//@Test
	public void testAddTodo() throws Exception {		//失因!
		
		Todo todo = new Todo();
		todo.setTodoName("燈砧隔奄 砺什闘");
		todo.setPlanId("10030");
		
		planService.addTodo(todo);
	}
	
	//@Test
	public void testUpdateTodoName() throws Exception {		//失因!
		
		Todo todo = new Todo();
		todo.setTodoName("燈砧戚硯 痕井 砺什闘");
		todo.setTodoId("10023");
		
		planService.updateTodoName(todo);
	}
	
	//@Test
	public void testDeleteTodo() throws Exception {		//失因!
		
		planService.deleteTodo("10022");
	}
	
	
	
	
	
	/*
	 * //@Test public void testUpdateUserSlot() throws Exception { //失因!
	 * 
	 * planService.updateUserSlot("testMember"); }
	 * 
	 * //@Test public void testFindUserId() throws Exception { //失因!
	 * 
	 * String userId = planService.findUserId("testMember");
	 * 
	 * if( userId != null ) { System.out.println(userId + " 還精 糎仙馬澗 噺据脊艦陥 "); }else
	 * { System.out.println("testMember"+ " 澗 糎仙馬走 省柔艦陥"); }
	 * 
	 * String userId2 = planService.findUserId("nonono");
	 * 
	 * if( userId2 != null ) { System.out.println(userId2 + " 還精 糎仙馬澗 噺据脊艦陥 ");
	 * }else { System.out.println("nonono"+ " 澗 糎仙馬走 省柔艦陥"); } }
	 */
	
}