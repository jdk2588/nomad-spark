/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.spark.scheduler.cluster.nomad

import org.apache.spark.{SparkConf, SparkContext}

private object MainThreadDiesAndLeavesChildRunning extends TestApplication {
  def main(args: Array[String]): Unit = {
    checkArgs(args)()

    val sc = new SparkContext(new SparkConf()
      .setAppName("Main thread leaves child running"))

    new Thread {
      override def run(): Unit = {
        while (true) {
          logInfo("Sleeping in child thread")
          Thread.sleep(1000)
        }
      }
    }.start()
    logInfo("Child thread spawned")

    throw new Exception("Whoops, there goes the main thread")
  }
}
