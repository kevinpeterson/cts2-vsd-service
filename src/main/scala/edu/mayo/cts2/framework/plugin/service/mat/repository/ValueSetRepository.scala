package edu.mayo.cts2.framework.plugin.service.mat.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import edu.mayo.cts2.framework.plugin.service.mat.model.ValueSet
import org.springframework.data.repository.query.Param
import org.springframework.data.jpa.repository.Query
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Page

@Repository
@Transactional
trait ValueSetRepository extends CrudRepository[ValueSet, String] {

  def findAll(pageable: Pageable): Page[ValueSet]
  
  @Query("select distinct vs from ValueSet vs where upper(vs.name) like upper(:query) or upper(vs.formalName) like upper(:query)")
  def findByAnyLikeIgnoreCase(@Param("query") query:String, pageable: Pageable): Page[ValueSet]
  
  @Query("select distinct vs from ValueSet vs join vs.properties vsp " +
  		"where vsp.name = :propertyName and upper(vsp.value) like upper(:query)" )
  def findByPropertyLikeIgnoreCase(
      @Param("propertyName") propertyName:String, 
      @Param("query") query:String, 
      pageable: Pageable): Page[ValueSet]

  @Query("select vs.currentVersion.id from ValueSet vs where vs.name = :name")
  def findCurrentVersionIdByName(@Param("name") name:String): String

  @Query("select distinct vs from ValueSet vs where vs.uri =:uri")
  def findOneByUri(@Param("uri") uri: String): ValueSet
  
}