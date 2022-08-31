package com.kyledinh.httpserver

import com.kyledinh.httpserver.download.DownloadApp
import com.kyledinh.httpserver.greet.GreetApp
// import dev.zio.quickstart.users.{InmemoryUserRepo, PersistentUserRepo, UserApp}
import zhttp.service.Server
import zio.*

object MainApp extends ZIOAppDefault:
  def run: ZIO[Environment with ZIOAppArgs with Scope,Any,Any] =
    Server.start(
      port = 8080,
      http = GreetApp() ++ DownloadApp()
    ).provide(
      // An layer responsible for storing the state of the `counterApp`
    //   ZLayer.fromZIO(Ref.make(0)),
      
      // To use the persistence layer, provide the `PersistentUserRepo.layer` layer instead
    //   InmemoryUserRepo.layer 
    )
