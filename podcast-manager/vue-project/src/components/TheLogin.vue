<script>
import Cookies from 'js-cookie'

export default {
  data() {
    return {
      username: '',
      password: '',
      email: '',
      error: null
    }
  },
  methods: {
    async login() {
      this.error = null

      try {
        const response = await fetch('http://localhost:8080/Users/login', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            username: this.username,
            password: this.password
          })
        })

        if (!response.ok) {
          const error = await response.json()
          this.error = error.message
        } else {
          const data = await response.json()
          console.log(data)
          const accessToken = data.accessToken
          const userId = data.id
          Cookies.set('accessToken', accessToken)
          Cookies.set('userId', userId)

          console.log("here is the token")
          console.log(accessToken)
          console.log("here is the user id")
          console.log(userId)

          // Save the authToken in a cookie or a state management library like Vuex
        }
      } catch (error) {
        this.error = error.message
        console.log(error)
      }
    },
    async newUser() {
      this.error = null

      try {
        const response = await fetch(`http://localhost:8080/Users/newUser`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            username: this.username,
            email: this.email,
            password: this.password
          })
        })
        window.location.reload()

        if (!response.ok) {
          const error = await response.json()
          this.error = error.message
        } else {
          const data = await response.json()
          console.log(data)
          console.log("here is the data")
        }
      } catch (error) {
        this.error = error.message
        console.log(error)
      }

    },
    openSignUpForm() {

      document.getElementById("signUpForm").style.display = "block";
    },
    closeSignUpForm() {

      document.getElementById("signUpForm").style.display = "none";
    },
    logout() {

      Cookies.remove('userId');
      Cookies.remove('accessToken');

    }
  }
}
</script>

<template>
  <div>
    <br>
    <form @submit.prevent="login">
      <label for="username">Username:</label>
      <input v-model="username" name="username" type="text" required>
      <br>
      <label for="password">Password:</label>
      <input v-model="password" name="password" type="password" required>
      <br>
      <button class="button" type="submit">Log in</button>
    </form>
    <button class="button" @click="logout()">log out</button>
    <button class="button" @click="openSignUpForm()">sign up</button>
  </div>

  <div class="form-popup" id="signUpForm">
    <form @submit.prevent="newPodcast" class="formContainer">
      <h1>make a new podcast</h1>

      <label for="username"><b>username: </b></label>
      <input v-model="username" type="text" name="username" required>

      <label for="email"><b>email: </b></label>
      <input v-model="email" type="text" name="email" required>

      <label for="password"><b>password: </b></label>
      <input v-model="password" type="text" name="password" required>

      <button type="submit" class="btn" @click="newUser(), closeSignUpForm()">submit</button>
      <button type="button" class="btn cancel" @click="closeSignUpForm()">Close</button>
    </form>
  </div>
</template> 
<style>
.coverImage {
  width: 30%;
}


.Btn {
  display: flex;
  justify-content: left;
}


.button {
  border: none;
  border-radius: 5px;
  background-color: #00BD7E;
  color: white;
  padding: 14px 20px;
  cursor: pointer;
}

.form-popup {

  text-align: center;



  color: black;

  display: none;
  position: fixed;
  left: 45%;
  top: 20%;
  transform: translate(-50%, 5%);
  border: 3px solid #999999;
  z-index: 9;
}

.formContainer {
  max-width: 600px;
  padding: 30px;
  background-color: #fff;
}

.formContainer input[type=text],
.formContainer input[type=password] {
  width: 100%;
  padding: 15px;
  margin: 5px 0 20px 0;
  border: none;
  background: #eee;
}

.formContainer input[type=text]:focus,
.formContainer input[type=password]:focus {
  background-color: #ddd;
  outline: none;
}

.formContainer .btn {
  padding: 12px 20px;
  border: none;
  background-color: #00BD7E;
  color: #fff;
  cursor: pointer;
  width: 100%;
  margin-bottom: 15px;
  opacity: 0.8;
}

.formContainer .cancel {
  background-color: #005a3c;
}

.formContainer .btn:hover,
.button:hover {
  opacity: 1;
}
</style>