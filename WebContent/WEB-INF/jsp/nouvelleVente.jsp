<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nouvelle Vente</title>
</head>
<body>
<form action="./TraitementArticle" method="post">
<h2>ENI-Enchères</h2>
<h2>Nouvelle vente</h2><br>
<span class="border-left"><img src="photoArticle" alt="Photo du produit" class="img-responsive"></span>
<p>
<label for="article">Article : &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</label>
<input type="text" name="nom" id="nom" required><br>
</p>
<p>
<label for="description">Description : &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</label>
<textarea id="description" name="description" rows="5" cols="50" required></textarea>
</p>
<p>
	<label for="categorie">Catégorie</label>
	<select id="categorie">
		<option value="defaut"></option>
		<option value="informatique">Informatique</option>
		<option value="ameublement">Ameublement</option>
		<option value="vetement">Vêtement</option>
		<option value="sportLoisirs">Sport & Loisirs</option>
</select>
</p>
<p>
	<label for="photoArticle">Photo de l'article:</label>
	<input type="file"
       id="photoArticle" name="photoArticle"
       accept="image/png, image/jpeg">
</p>

<p>
	<label for="debutEnchere">Début de l'enchère</label>
	<input type="date" id="debutEnchere" name="debutEnchere" min="<%LocalDate.now();%>">
</p>


<p>
	<label for="finEnchere">Fin de l'enchère</label>
	<input type="date" id="finEnchere" name="finEnchere" >
</p>
<p>
	<label for="defPrix">Mise à prix :</label>
	<input type="number" id="defPrix" name="defPrix" min="1" step="1" required>
</p>
<fieldset>
	<legend>Retrait</legend>

	<label for="rue">Rue : </label>
	<input type="text" name="rue" id="rue" placeholder="<%=request.getParameter("rue")%>" required><br>
	
	
	<label for="codePostal">Code Postal : </label>
	<input type="text" name="codePostal" id="codePostal" placeholder="<%=request.getParameter("codePostal")%>" required><br>
	
	
	<label for="ville">Ville : </label>
	<input type="text" name="ville" id="ville" placeholder="<%=request.getParameter("ville")%>" required><br>
</fieldset>
<button type="submit" name="enregistrer" id="enregistrer">Enregistrer</button>
<a href="WebContent/WEB-INF/jsp/listeEncheres.jsp" ><button type="reset" name="annuler" id="annuler">Annuler</button></a>

<%
if (request.getAttribute("noArticle") != null) {
%>     
<button type="submit" name="annuler" id="annuler">Annuler la vente</button>
<%
} 
%>





</form>
</body>
</html>