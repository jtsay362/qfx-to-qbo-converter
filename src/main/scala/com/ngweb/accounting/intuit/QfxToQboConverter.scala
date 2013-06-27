package com.ngweb.accounting.intuit

import java.io.Reader
import java.io.Writer
import java.io.FileReader
import java.io.FileWriter
import java.io.BufferedReader

import scala.util.matching.Regex

class QfxToQboConverter {
	
}

object QfxToQboConverter {
  
  def main(args : Array[String]) : Unit = {
    
    if ((args.length == 0) || (args.length > 3)) {
        usage()
        return
    }
    
    var in : FileReader = null
    var out : FileWriter = null
    var code = 2430
    
    val inFilename = args(0)
    in = new FileReader(inFilename)
    
    if (args.length < 2) {
      out = new FileWriter(if (inFilename.toLowerCase().endsWith(".qfx")) {
        inFilename.substring(0, inFilename.length - 3) + "qbo"           
      } else {
        inFilename + ".qbo"
      }); 
    } else {
      out = new FileWriter(args(1))
    }
        
    if (args.length >= 2) {
      code = args(2).toInt
    }
        
    convert(in, out, code)        
  }  

  def usage() {
    println("java " + classOf[QfxToQboConverter].getName + 
        " <infile> [outfile [code]]")
  }
    
  def convert
  (
    reader : Reader,
    writer : Writer,
    code : Int
  ) : Unit =   
  {
    try {
  		val br = new BufferedReader(reader)
  		  		
  		var done = false;
  		
  		do {
  		  val line = br.readLine();
  		  
  		  done = if (line == null) {  		    
  		    true
  		  } else {  		    
  		  	writer.write(
  		  			_DATA_LINE.replaceFirstIn(line, "$1" + code + "$2") + "\n")
  		      		    
  		    false
  		  }
  		    		    		    		  
  		} while (!done);
    } finally {
      try {
      	reader.close()
      } finally {
      	writer.close()
    	}
    }  		
  }

  private[this] val _DATA_LINE = """^(<OFX>.+<INTU.BID>)\d+(.+)""".r 
}