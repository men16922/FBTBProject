<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


	
		        <div class="row">
   
	
	
        
        <!--======================= 차트 캔버스 시작 ======================-->
        <div><h2><br>올해 월 별 입사자</h2></div>
				<canvas class="my-4" id="myChart" width="80" height="30"></canvas>

   
    <!-- Bootstrap core JavaScript
    ================================================== -->


    <!-- Graphs -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.1/Chart.min.js"></script>
    <script>
      var ctx = document.getElementById("myChart");
      var myChart = new Chart(ctx, {
        type: 'bar',
        data: {
          labels: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"],
          datasets: [{
            data: [${count1}, ${count2}, ${count3}, ${count4}, ${count5}, ${count6}, ${count7},${count8}, ${count9}, ${count10}, ${count11}, ${count12}],
            lineTension: 0,
            backgroundColor: '#007bff',
            borderColor: '#007bff',
            borderWidth: 5,
            pointBackgroundColor: '#007bff'
          }]
        },
        options: {
          scales: {
            yAxes: [{
              ticks: {
                beginAtZero: false
              }
            }]
          },
          legend: {
            display: false,
          }
        }
      });
    </script>
    
    	
      
        <!--======================= 차트 캔버스 끝======================-->
     
        </div>
</body>
</html>