package documents.portlet.list.controllers;

import juzu.*;
import juzu.template.Template;

import javax.inject.Inject;
import java.io.IOException;

/** @author <a href="mailto:benjamin.paillereau@exoplatform.com">Benjamin Paillereau</a> */
@SessionScoped
public class DocumentsApplication extends juzu.Controller
{

  /** . */
  @Inject
  @Path("index.gtmpl")
  Template indexTemplate;

  @Inject
  @Path("files.gtmpl")
  Template filesTemplate;

  @Inject
  DocumentsData documentsData;

  @View
  public void index() throws IOException
  {
    indexTemplate.with().set("type", DocumentsData.TYPE_DOCUMENT).render();
  }

  @Resource
  public void getFiles(String type)
  {
    filesTemplate.with().set("files", documentsData.getNodes(type)).render();
  }

  @Resource
  public void deleteFile(String uuid, String type)
  {
    System.out.println(uuid+"::"+type);
    documentsData.deleteFile(uuid);
    filesTemplate.with().set("files", documentsData.getNodes(type)).render();
  }

  @Resource
  public Response.Content renameFile(String uuid, String name)
  {
    try
    {
      documentsData.renameFile(uuid, name);
    }
    catch (IllegalArgumentException e)
    {
      return Response.notFound(e.getMessage());
    }
    catch (Exception e)
    {
      return Response.notFound("Your file cannot be renamed. Please, try later");
    }
    return Response.ok("Successfully renamed.");
  }




}
