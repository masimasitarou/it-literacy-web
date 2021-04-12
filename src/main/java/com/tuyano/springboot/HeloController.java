package com.tuyano.springboot;

import javax.annotation.PostConstruct;
import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.aspectj.weaver.tools.cache.AsynchronousFileCacheBacking.RemoveCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.tuyano.springboot.repositries.MyDataRepository;
import com.tuyano.springboot.repositries.UrlDataRepository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HeloController {
	@Autowired
	MyDataRepository repository;
	@Autowired
	UrlDataRepository repository2;

	MyDataDaoImpl dao;

	UrlDataDaoImpl dao2;

	@PersistenceContext
	EntityManager entityManager;

	//サーバー起動時に自動的に実行
	@PostConstruct
	public void init() throws IOException {
		dao = new MyDataDaoImpl(entityManager); // ●
		dao2 = new UrlDataDaoImpl(entityManager);
		FileReader fileReader = null;

		// MyDataのCSVファイルを読み込む
		try {
			fileReader = new FileReader("testfile.csv");
			List<MyData> dataObjList = new CsvToBeanBuilder<MyData>(fileReader).withType(MyData.class).build().parse();
			//csvファイルから1行ずつデータを取り出してMyDataテーブルに値をセットしていく。
			for (MyData obj : dataObjList) {
				MyData d1 = new MyData();
				d1.setId(obj.getId());
				d1.setTitle(obj.getTitle());
				d1.setMemo(obj.getMemo());
				repository.saveAndFlush(d1);
			}
		} finally {
			if (fileReader != null) {
				fileReader.close();
			}
		}
		// UrlDataのCSVファイルを読み込む
		try {
			fileReader = new FileReader("urlfile.csv");
			List<UrlData> urlObjList = new CsvToBeanBuilder<UrlData>(fileReader).withType(UrlData.class).build()
					.parse();
			// csvファイルから1行ずつデータを取り出してUrlDataテーブルに値をセットしていく。
			for (UrlData obj : urlObjList) {
				UrlData d2 = new UrlData();
				d2.setUrl(obj.getUrl());
				d2.setId(obj.getId());
				System.out.println(d2.getUrl());
				repository2.saveAndFlush(d2);
			}
		} finally {
			if (fileReader != null) {
				fileReader.close();
			}
		}
	}
	
	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public ModelAndView find(ModelAndView mav) {
		mav.setViewName("find");
		mav.addObject("title", "Find Page");
		mav.addObject("msg", "検索したいワードを入力してください！");
		mav.addObject("value", "");
		Iterable<MyData> list = dao.getAll(); 
		mav.addObject("datalist", list);
		return mav;
	}

	@RequestMapping(value = "/find2", method = RequestMethod.GET)
	public ModelAndView find2(ModelAndView mav) {
		mav.setViewName("find");
		mav.addObject("title", "Find Page");
		mav.addObject("msg", "検索したいワードを入力してください！");
		mav.addObject("value", "");
		Iterable<MyData> list = dao.getAll(); // ●
		mav.addObject("datalist", list);
		return mav;
	}

	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public ModelAndView search(HttpServletRequest request, ModelAndView mav) {
		mav.setViewName("find");
		String param = request.getParameter("fstr");
		if (param == "") {
			mav = new ModelAndView("redirect:/find");
		} else {
			mav.addObject("title", "Find result");
			mav.addObject("msg", "「" + param + "」の検索結果");
			mav.addObject("value", param);
			List<MyData> list = dao.find(param);// ●
			mav.addObject("datalist", list);
		}
		return mav;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	// @ModelAttribute("formModel") MyData mydataで、エンティティクラスのインスタンスを用意
	public ModelAndView index(ModelAndView mav) {
		// htmlファイルの指定
		mav.setViewName("index");
		mav.addObject("msg", "登録ページ");
		return mav;
	}

	// 登録
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@Transactional(readOnly = false)
	public ModelAndView form(@RequestParam("title") String title, @RequestParam("memo") String memo,
			@RequestParam("url1") String url1, @RequestParam("url2") String url2, @RequestParam("url3") String url3,
			@Validated MyData mydata, @Validated UrlData urldata1, BindingResult result, ModelAndView mov)
			throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
		ModelAndView res = null;
		if (result.hasErrors()){
			mov.setViewName("index");
			mov.addObject("msg", "入力エラーがあります。");
			Iterable<MyData> list = repository.findAll();
			mov.addObject("datalist", list);
			res = mov;
			return res;
		}
			// MyDataに格納されているテーブルのデータを全て抜き出してリストに入れる。
			// 今の時間をとって文字列結合したものをIDとする。
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("ddhhmmss");
			String str = sdf.format(date);
			int id = Integer.parseInt(str);
			mydata.setId(id);
			mydata.setTitle(title);
			mydata.setMemo(memo);
			repository.saveAndFlush(mydata);
			// 入力フォームに値が入っていればそのデータを保存し、空だった場合は処理を飛ばして次の処理に入る。
			if (url1 != "") {
				urldata1.setId(id);
				urldata1.setUrl(url1);
				repository2.saveAndFlush(urldata1);
			}

			if (url2 != "") {
				UrlData urldata2 = new UrlData();
				urldata2.setId(id);
				urldata2.setUrl(url2);
				repository2.saveAndFlush(urldata2);
			}

			if (url3 != "") {
				UrlData urldata3 = new UrlData();
				urldata3.setId(id);
				urldata3.setUrl(url3);
				repository2.saveAndFlush(urldata3);
			}

			mov.addObject("message", "完了！");
			// MyData,UrlDataに保存した値を全て取り出してリストに入れる。
			List<MyData> list2 = dao.getAll();
			List<UrlData> list3 = dao2.getAll();
			FileWriter fileWriter = null;

			// testfile.csvにMyDataのデータを書き込む
			try {
				fileWriter = new FileWriter("testfile.csv");
				StatefulBeanToCsvBuilder<MyData> builder = new StatefulBeanToCsvBuilder<MyData>(fileWriter);
				StatefulBeanToCsv<MyData> beanToCsv = builder.build();
				beanToCsv.write(list2);
			} finally {
				if (fileWriter != null) {
					fileWriter.close();
				}
			}
			// urlfile.csvにUrlDataの値を書き込む
			try {
				fileWriter = new FileWriter("urlfile.csv");
				StatefulBeanToCsvBuilder<UrlData> builder = new StatefulBeanToCsvBuilder<UrlData>(fileWriter);
				StatefulBeanToCsv<UrlData> beanToCsv = builder.build();
				beanToCsv.write(list3);
			} finally {
				if (fileWriter != null) {
					fileWriter.close();
				}
			}
			res = new ModelAndView("redirect:/find");

		return res;
	}

	// 入力されているデータの更新
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@Transactional(readOnly = false)
	public ModelAndView update(@RequestParam("title") String title, @RequestParam("memo") String memo,
			@RequestParam("ID") long id, @RequestParam("url1") String url1, @RequestParam("url2") String url2,
			@RequestParam("url3") String url3, @Validated MyData mydata, @Validated UrlData urldata1,
			BindingResult result, ModelAndView mov)
			throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
		ModelAndView res = null;
		if (!result.hasErrors()) {
			// MyDataに格納されているテーブルのデータを全て抜き出してリストに入れる。
			List<MyData> list = dao.getAll();
			// 現在MyDataに格納されているリストにある最大値を数え、その数をIDとする。
			// 今の時間をとって文字列結合したものをIDとする。
			long newId = list.get(list.size() - 1).getId() + 1;
			mydata.setId(newId);
			mydata.setTitle(title);
			mydata.setMemo(memo);
			repository.saveAndFlush(mydata);
			// 入力フォームに値が入っていればそのデータを保存し、空だった場合は処理を飛ばして次の処理に入る。
			//1つめのフォームの値をチェック
			if (url1 != "") {
				urldata1.setId(newId);
				urldata1.setUrl(url1);
				repository2.saveAndFlush(urldata1);
			}
			//2つめのフォームの値をチェック
			if (url2 != "") {
				UrlData urldata2 = new UrlData();
				urldata2.setId(newId);
				urldata2.setUrl(url2);
				repository2.saveAndFlush(urldata2);
			}
			//3つめのフォームの値をチェック
			if (url3 != "") {
				UrlData urldata3 = new UrlData();
				urldata3.setId(newId);
				urldata3.setUrl(url3);
				repository2.saveAndFlush(urldata3);
			}

			// 編集前の情報を削除する。
			mydata = dao.findById((long) id);
			List<UrlData> urldata = dao2.findAll((long) id);

			entityManager.remove(mydata);

			for (int i = 0; i < urldata.size(); i++) {
				entityManager.remove(urldata.get(i));
			}

			mov.addObject("msg", "登録しました！");
			// MyData,UrlDataに保存した値を全て取り出してリストに入れる。
			List<MyData> list2 = dao.getAll();
			List<UrlData> list3 = dao2.getAll();
			FileWriter fileWriter = null;

			// testfile.csvにMyDataのデータを書き込む
			try {
				fileWriter = new FileWriter("testfile.csv");
				StatefulBeanToCsvBuilder<MyData> builder = new StatefulBeanToCsvBuilder<MyData>(fileWriter);
				StatefulBeanToCsv<MyData> beanToCsv = builder.build();
				beanToCsv.write(list2);
			} finally {
				if (fileWriter != null) {
					fileWriter.close();
				}
			}
			// urlfile.csvにUrlDataの値を書き込む
			try {
				fileWriter = new FileWriter("urlfile.csv");
				StatefulBeanToCsvBuilder<UrlData> builder = new StatefulBeanToCsvBuilder<UrlData>(fileWriter);
				StatefulBeanToCsv<UrlData> beanToCsv = builder.build();
				beanToCsv.write(list3);
			} finally {
				if (fileWriter != null) {
					fileWriter.close();
				}
			}
			res = new ModelAndView("redirect:/find");
		} else {
			//書き込みがで着なかった場合エラーを表示する。
			mov.setViewName("index");
			mov.addObject("msg", "入力エラーがあります。");
			Iterable<MyData> list = repository.findAll();
			mov.addObject("datalist", list);
			res = mov;
		}
		return res;
	}

	@RequestMapping(value = "/postId", method = RequestMethod.POST)
	public String postId(RedirectAttributes redirectAttributes, @RequestParam("titleID") String id
	// @RequestParam("title") String title2, @RequestParam("memo") String memo2
	) {
		redirectAttributes.addFlashAttribute("model", id);
		return "redirect:/editData";
	}

	@RequestMapping("/editData")
	public ModelAndView editData(@ModelAttribute("model") long id, ModelAndView mav) {

		mav.setViewName("editFile");
		MyData data = dao.findById((long) id);

		mav.addObject("ID", data.getId());
		mav.addObject("title", data.getTitle());
		mav.addObject("memo", data.getMemo());

		// UrlDataに格納されているデータの数だけURLを取り出して画面に出力する。
		List<UrlData> data2 = dao2.findAll((long) id);
		for (int i = 0; i < data2.size(); i++) {
			Integer num = Integer.valueOf(i) + 1;
			mav.addObject("url" + num.toString(), data2.get(i).getUrl());
		}
		return mav;
	}

	@RequestMapping(value = "/postIdForDelete", method = RequestMethod.POST)
	public String formForDelete(RedirectAttributes redirectAttributes, @RequestParam("titleID") String id
	// @RequestParam("title") String title2, @RequestParam("memo") String memo2
	) {
		redirectAttributes.addFlashAttribute("model", id);
		return "redirect:/deleteConfirm";
	}

	// 登録した内容の削除
	@RequestMapping(value = "/deleteConfirm", method = RequestMethod.GET)
	public ModelAndView delete(@ModelAttribute("model") long id, ModelAndView mav) {
		mav.setViewName("delete");

		MyData data = dao.findById((long) id);

		mav.addObject("ID", data.getId());
		mav.addObject("title", data.getTitle());
		mav.addObject("memo", data.getMemo());

		List<UrlData> data2 = dao2.findAll((long) id);
		for (int i = 0; i < data2.size(); i++) {
			Integer num = Integer.valueOf(i) + 1;
			System.out.println("ID: " + data2.get(i).getUrl());
			mav.addObject("url" + num.toString(), data2.get(i).getUrl());
		}
		return mav;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@Transactional(readOnly = false)
	public ModelAndView remove(@RequestParam long id, ModelAndView mav)
			throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
		ModelAndView res = null;
		MyData mydata = dao.findById((long) id);
		List<UrlData> urldata = dao2.findAll((long) id);

		entityManager.remove(mydata);

		for (int i = 0; i < urldata.size(); i++) {
			entityManager.remove(urldata.get(i));
		}

		List<MyData> list = dao.getAll();
		List<UrlData> list2 = dao2.getAll();

		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter("testfile.csv");
			StatefulBeanToCsvBuilder<MyData> builder = new StatefulBeanToCsvBuilder<MyData>(fileWriter);
			StatefulBeanToCsv<MyData> beanToCsv = builder.build();
			beanToCsv.write(list);
		} finally {
			if (fileWriter != null) {
				fileWriter.close();
			}
		}
		// urlfile.csvにUrlDataの値を書き込む
		try {
			fileWriter = new FileWriter("urlfile.csv");
			StatefulBeanToCsvBuilder<UrlData> builder = new StatefulBeanToCsvBuilder<UrlData>(fileWriter);
			StatefulBeanToCsv<UrlData> beanToCsv = builder.build();
			beanToCsv.write(list2);
			mav.addObject("msg", "登録ページ");
		} finally {
			if (fileWriter != null) {
				fileWriter.close();
			}
		}
		res = new ModelAndView("redirect:/find");
		return res;
	}

	// クリックしたMyDataのIDを受け取って /forward_testに渡し、リダイレクトする。
	@RequestMapping(value = "/post_test", method = RequestMethod.POST)
	public String form(RedirectAttributes redirectAttributes, @RequestParam("titleID") String id
	// @RequestParam("title") String title2, @RequestParam("memo") String memo2
	) {
		redirectAttributes.addFlashAttribute("model", id);
		return "redirect:/forward_test";
	}

	// formメソッドからMyDataのIDを受け取り、そのIDをもとにURLDataとMyDataをリスト表示する。
	@RequestMapping("/forward_test")
	public ModelAndView sample(@ModelAttribute("model") long id, ModelAndView mav) {

		mav.setViewName("forward_test");
		MyData data = dao.findById((long) id);

		mav.addObject("ID", data.getId());
		mav.addObject("title", data.getTitle());
		mav.addObject("memo", data.getMemo());

		// UrlDataに格納されているデータの数だけURLを取り出して画面に出力する。
		List<UrlData> data2 = dao2.findAll((long) id);
		for (int i = 0; i < data2.size(); i++) {
			Integer num = Integer.valueOf(i) + 1;
			mav.addObject("url" + num.toString(), data2.get(i).getUrl());
		}

		return mav;
	}

	@RequestMapping("/login")
	public ModelAndView login(ModelAndView mav) {
		mav.setViewName("login");
		return mav;
	}

	@RequestMapping("/home")
	public ModelAndView home(ModelAndView mav) {
		mav.setViewName("home");
		return mav;
	}

	@RequestMapping("/account_registration")
	public ModelAndView account_registration(ModelAndView mav) {
		mav.setViewName("account_registration");
		return mav;
	}

}