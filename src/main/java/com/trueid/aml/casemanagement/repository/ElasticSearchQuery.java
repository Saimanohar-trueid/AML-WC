package com.trueid.aml.casemanagement.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.trueid.aml.casemanagement.model.ElasticARData;
import com.trueid.aml.casemanagement.model.ElasticMain;
import com.trueid.aml.casemanagement.model.Product;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.DeleteRequest;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;

@Repository
public class ElasticSearchQuery {
	private static final Logger log = LoggerFactory.getLogger(ElasticSearchQuery.class);

	@Autowired
	private ElasticsearchClient elasticsearchClient;

	private static final String indexName = "product";

	public String createOrUpdateDocument(Product product) throws IOException {

		IndexResponse response = elasticsearchClient
				.index(i -> i.index(indexName).id(product.getId()).document(product));
		if (response.result().name().equals("Created")) {
			return new StringBuilder("Document has been successfully created.").toString();
		} else if (response.result().name().equals("Updated")) {
			return new StringBuilder("Document has been successfully updated.").toString();
		}
		return new StringBuilder("Error while performing the operation.").toString();
	}

	public String create(ElasticMain index) throws IOException {

		IndexResponse response = elasticsearchClient
				.index(i -> i.index("rni-wd-en").id(null).document(index));
		if (response.result().name().equals("Created")) {
			return new StringBuilder("Document has been successfully created.").toString();
		} else if (response.result().name().equals("Updated")) {
			return new StringBuilder("Document has been successfully updated.").toString();
		}
		return new StringBuilder("Error while performing the operation.").toString();
	}

	public String createCSV(ElasticARData product) throws IOException {

		IndexResponse response = elasticsearchClient
				.index(i -> i.index("rni-wd-en").id(null).document(product));
		if (response.result().name().equals("Created")) {
			return new StringBuilder("Document has been successfully created.").toString();
		} else if (response.result().name().equals("Updated")) {
			return new StringBuilder("Document has been successfully updated.").toString();
		}

		return new StringBuilder("Error while performing the operation.").toString();
	}

	public Product getDocumentById(String productId) throws IOException {
		Product product = null;
		GetResponse<Product> response = elasticsearchClient.get(g -> g.index(indexName).id(productId), Product.class);

		if (response.found()) {
			product = response.source();
			log.info("Product name " + product.getName());
		} else {
			log.info("Product not found");
		}

		return product;
	}

	public String deleteDocumentById(String productId) throws IOException {

		DeleteRequest request = DeleteRequest.of(d -> d.index(indexName).id(productId));

		DeleteResponse deleteResponse = elasticsearchClient.delete(request);
		if (Objects.nonNull(deleteResponse.result()) && !deleteResponse.result().name().equals("NotFound")) {
			return new StringBuilder("Product with id " + deleteResponse.id() + " has been deleted.").toString();
		}
		log.info("Product not found");
		return new StringBuilder("Product with id " + deleteResponse.id() + " does not exist.").toString();

	}

	public List<Product> searchAllDocuments() throws IOException {

		SearchRequest searchRequest = SearchRequest.of(s -> s.index(indexName));
		SearchResponse searchResponse = elasticsearchClient.search(searchRequest, Product.class);
		List<Hit> hits = searchResponse.hits().hits();
		List<Product> products = new ArrayList<>();
		for (Hit<?> object : hits) {

			products.add((Product) object.source());

		}
		return products;
	}

}