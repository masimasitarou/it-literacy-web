package com.tuyano.springboot;

//MyDataDaoインターフェースをここで実装する。
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import javax.persistence.Query;

@Repository
public class MyDataDaoImpl implements MyDataDao<MyData> {
	//クラスローダーの優先順位を決めている
	private static final long serialVersionUID = 1L;

	private EntityManager entityManager;

	public MyDataDaoImpl() {
		super();
	}

	public MyDataDaoImpl(EntityManager manager) {
		this();
		entityManager = manager;
	}

	@Override
	public List<MyData> getAll() {
		List<MyData> list = null;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<MyData> query = builder.createQuery(MyData.class);
		Root<MyData> root = query.from(MyData.class);
		query.select(root);
		list = (List<MyData>) entityManager.createQuery(query).getResultList();
		return list;
	}
	
	//MyDataのIDをもとに、そのMyDataに含まれる行の情報を全て検索し、リストに入れて返す。
	@SuppressWarnings("unchecked")
	@Override
	public List<MyData> findAll(long id) {
		return entityManager.createQuery("from MyData where id = " + id).getResultList();
	}
	
	//MyDataのIDをもとに、そのMyDataに含まれる行の情報を1つ検索し、リストに入れて返す。
	@Override
	public MyData findById(long id) {
		return (MyData)entityManager.createQuery("from MyData where id = " + id).getSingleResult();
	}

	//MyDataに含まれる情報を名前から検索してその行の情報を返す。
	@SuppressWarnings("unchecked")
	@Override
	public List<MyData> findByName(String name) {
		return (List<MyData>) entityManager.createQuery("from MyData where name =" + name).getResultList();
	}
	
	//MyDataに含まれる情報をタイトルIDから検索してその行の情報を返す。
	@Override
	public MyData findByTitleId(long id) {
		return (MyData)entityManager.createQuery("from MyData where titleId =" + id).getSingleResult();
	}
	
	
	//検索フォームに文字列を入れると、メモやタイトルにその文字列が含まれていればリスト表示する。
	@SuppressWarnings("unchecked")
	@Override
	//フォームで受け取った値を変数fstrに代入
	public List<MyData> find(String fstr){
		List<MyData> list = null;
		//メモ、ID、タイトルに含まれる文字列をここで検索
		String qstr = "from MyData where id = :fid or title like :ftitle or memo like :fmemo";
		Long fid = 0L;
		try {
			fid = Long.parseLong(fstr);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		Query query = entityManager.createQuery(qstr).setParameter("fid", fid)
				.setParameter("ftitle", "%" + fstr + "%")
				.setParameter("fmemo", "%" + fstr + "%");
		list = query.getResultList();
		return list;
	}
	

}