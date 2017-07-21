const express = require('express')
const uuidv4 = require('uuid/v4')
const app = express()

app.get('/', function (req, res) {
  res.send('Hello CS499!')
})

app.get('/courses', function (req, res) {
	setTimeout(function(){
		var courses = [];
		for(var i = 0; i < 10; i++) {
			var credit = Math.floor(Math.random() * 4) + 1;
			var number = Math.floor(Math.random() * 600) + 100;
			var c = {
				'id' : i,
				'courseName' : uuidv4(),
				'courseId' : 'CS' + number,
				'courseCredit' : credit
			};		
			courses.push(c);
		}
		res.send(courses);
	}, 5000);	
})

app.listen(3000, function () {
  console.log('Example app listening on port 3000!')
})