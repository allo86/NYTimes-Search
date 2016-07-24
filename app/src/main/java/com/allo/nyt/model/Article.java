package com.allo.nyt.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ALLO on 24/7/16.
 */
@Parcel
public class Article {

    @SerializedName("_id")
    String articleId;

    @SerializedName("web_url")
    String webUrl;

    @SerializedName("snippet")
    String snippet;

    @SerializedName("lead_paragraph")
    String leadParagraph;

    @SerializedName("print_page")
    String printPage;

    @SerializedName("source")
    String source;

    @SerializedName("headline")
    Headline headline;

    @SerializedName("keywords")
    ArrayList<Keyword> keywords;

    @SerializedName("pub_date")
    Date pubDate;

    @SerializedName("document_type")
    String documentType;

    @SerializedName("news_desk")
    String newsDesk;

    @SerializedName("section_name")
    String sectionName;

    @SerializedName("subsection_name")
    String subsectionName;

    @SerializedName("type_of_material")
    String typeOfMaterial;

    public Article() {

    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getLeadParagraph() {
        return leadParagraph;
    }

    public void setLeadParagraph(String leadParagraph) {
        this.leadParagraph = leadParagraph;
    }

    public String getPrintPage() {
        return printPage;
    }

    public void setPrintPage(String printPage) {
        this.printPage = printPage;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Headline getHeadline() {
        return headline;
    }

    public void setHeadline(Headline headline) {
        this.headline = headline;
    }

    public ArrayList<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(ArrayList<Keyword> keywords) {
        this.keywords = keywords;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getNewsDesk() {
        return newsDesk;
    }

    public void setNewsDesk(String newsDesk) {
        this.newsDesk = newsDesk;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSubsectionName() {
        return subsectionName;
    }

    public void setSubsectionName(String subsectionName) {
        this.subsectionName = subsectionName;
    }

    public String getTypeOfMaterial() {
        return typeOfMaterial;
    }

    public void setTypeOfMaterial(String typeOfMaterial) {
        this.typeOfMaterial = typeOfMaterial;
    }
}
