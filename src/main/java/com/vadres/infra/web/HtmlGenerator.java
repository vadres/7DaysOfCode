package com.vadres.infra.web;

import com.vadres.domain.models.Content;

import java.io.PrintWriter;
import java.util.List;

public class HtmlGenerator {
	private final PrintWriter writer;

	public HtmlGenerator(PrintWriter writer) {
		this.writer = writer;
	}

	public void generate(List<? extends Content> contents) {
		writer.println("""
            <html>
                <head>
                     <meta charset="utf-8">
                     <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
                     <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
                </head>
                <body>
                    <div class="container">
                    <div class="row">
        """);

		for (Content content : contents) {
			String div = """
                  <div class="col-lg-4 col-md-3 col-sm-2"  style="padding: 5px 15px">
                  <div class="card" style="min-height: 100%%;">
                       <img class="card-img-top" src="%s" alt="%s">
                       <div class="card-body">
                           <h4 class="card-title">%s</h4>
                           <h5 class="card-text mt-2"  style="display:flex;justify-content:space-between">
                               <div>Nota: <span class="badge bg-success">%s</span></div> 
                               <div>Ano: <span class="badge bg-primary">%s</div>
                           </h5>
                       </div>
                  </div>
                  </div>
             """;

			writer.println(String.format(div, content.url(), content.title(), content.title(), content.rating(), content.year()));
		}


		writer.println("""
                </div>
                </div>
             </body>
             </html>
        """);
	}
}
