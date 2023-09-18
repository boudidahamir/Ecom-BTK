package com.btk.bean;

import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;

import com.btk.entity.Product;
import com.btk.services.ProductDao;

@ManagedBean(name = "productBean")
@ViewScoped
public class ProductBean {

	private int id;
	private String name;
	private String description;
	private int unit;
	private float price;
	private Part imagePart;
	private String search;
	private Product prod;
	private int sold;

	private List<Product> listProd;

	@EJB
	private ProductDao ProdDao;

	@PostConstruct
	public void OnInit() {
		testGetData();
	}

	public void testGetData() {
		System.out.println("search: " + search);

		if (search == null || search.equals("")) {
			listProd = ProdDao.getData0();
			System.out.println("if: " + listProd);

		} else if (search != null && !search.equals("")) {
			listProd = ProdDao.getProdByData(search);
			System.out.println("else: " + listProd);

		} else
			listProd = new ArrayList<Product>();
	}
	

	public List<Product> getProdbyid(int id) {
		List<Product> listProd = ProdDao.getProductById(id);
		System.out.println("if: " + listProd);
		return listProd;
	}

	public InputStream getImageStream(int id) throws IOException, SQLException {
		if (id != 0) {
			System.out.println("id: " + id);
			Blob imageBlob = ProdDao.getImageDataById(id); // Assuming imageController is properly injected
			if (imageBlob != null) {
				return imageBlob.getBinaryStream();
			}
		}
		return null;
	}

	public void afficher() {
		System.out.println("Produit = " + prod);
	}

	public String getImageBase64(int id) throws IOException, SQLException {
		InputStream imageStream = getImageStream(id);
		if (imageStream != null) {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = imageStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			byte[] imageBytes = outputStream.toByteArray();
			return Base64.getEncoder().encodeToString(imageBytes);
		}
		return null;
	}

	public void deleteProduct(int id) {
		ProdDao.delete(id);
		testGetData();
	}

	public String addProduct() {
		System.out.println("name=" + name);
		try (InputStream inputStream = imagePart.getInputStream()) {
			byte[] imageBytes = new byte[inputStream.available()];
			inputStream.read(imageBytes);
			Blob imageBlob = new javax.sql.rowset.serial.SerialBlob(imageBytes);

			ProdDao.add(name, description, imageBlob, unit, 0);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		return "listproductAdmin.xhtml?faces-redirect=true";
	}

	public void updateProduct(Product us) {
		prod = us;
		System.out.println("jdida " + prod);

//		return "updateProduct.xhtml?faces-redirect=true";
	}

	public void updateProduct1() {
		if (imagePart != null && imagePart.getSize() > 0)
			try (InputStream inputStream = imagePart.getInputStream()) {
				byte[] imageBytes = new byte[inputStream.available()];
				inputStream.read(imageBytes);
				Blob imageBlob = new javax.sql.rowset.serial.SerialBlob(imageBytes);
				this.prod.setImage(imageBlob);
				this.ProdDao.update(this.prod);
			} catch (IOException | SQLException e) {
				e.printStackTrace();
			}
		else {
			this.ProdDao.update(this.prod);
		}
//		return "listproductAdmin.xhtml?faces_redirect=true";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public ProductDao getProdDao() {
		return ProdDao;
	}

	public void setProdDao(ProductDao prodDao) {
		ProdDao = prodDao;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public Product getProd() {
		return prod;
	}

	public void setProd(Product prod) {
		this.prod = prod;
	}

	public Part getImagePart() {
		return imagePart;
	}

	public void setImagePart(Part imagePart) {
		this.imagePart = imagePart;
	}

	public int getSold() {
		return sold;
	}

	public void setSold(int sold) {
		this.sold = sold;
	}

	public List<Product> getListProd() {
		return listProd;
	}

	public void setListProd(List<Product> listProd) {
		this.listProd = listProd;
	}

}
