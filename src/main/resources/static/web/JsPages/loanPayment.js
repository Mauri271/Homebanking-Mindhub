const { createApp } = Vue

const app = createApp({
  data() {
  return {
    logged: false,
    loans: [],
    clientLoanId:0,
    accountNumber:""

      };
  },

  created(){
    this.clientLoans()
  },

  methods:{
    logOut(){
      axios.post('/api/logout')
      .then(response => window.location.href="../htmlPages/login.html")
    },

    clientLoans(){
        axios.get("/api/clients/current")
        .then(res=> {
            console.log(res)
            this.loans = res.data.loans
            this.accounts = res.data.accounts
            
        })
        .catch(err => console.log(err))
    },

    

    payLoan(){

      if(this.clientLoanId == false){
        alert('Please select a Loan')
      } if(this.accountNumber == false){
        alert('Please select an account')
      } else {
        axios.post('/api/loans/loanPayment',`clientLoanId=${this.clientLoanId}&accountNumber=${this.accountNumber}`)
        .then(res => 
         alert(res.data))
        .catch(err => alert(err.response.data))
      }
      }
      


    
  }

})
  .mount('#app')