const { createApp } = Vue

const app = createApp({
  data() {
  return {
    accounts:{},
    loansData:{},
    selectedLoan:{},
    destinationAccount:"",
    amount:0,
    payments:"",
    interests:0
      }
      },

  created(){
    this.loadData()
    this.dataLoans()
  },

  methods:{


    //get accounts 
    loadData(){
        axios.get('/api/clients/current')
        .then( res => {
            this.accounts = res.data.accounts
            console.log(this.accounts)
        })
        .catch(err => {
            console.log(err)
        })
    },

    //get loans

    dataLoans(){
        axios.get('/api/loans')
        .then(res => {
            this.loansData = res.data
            console.log(this.loansData)
            
        })
        .catch(err => {
            console.log(err)
        })
    },

    //logout

    logOut(){
      axios.post('/api/logout')
      .then(response => {
        console.log(response)
        window.location.href="../htmlPages/login.html"
      })
      .catch(err => console.log(err))
    },


    //alerts
    isTheclientSure(){
        Swal.fire({
            title: 'Are you sure?',
            text: "You won't be able to revert this!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes!'
          }).then((result) => {
            if (result.isConfirmed) {
              Swal.fire(
                'Created!',
                'Your loan has been approved.',
                'success',
                this.loanRequest()
              )
            }
          })
    },


                //request loan
  loanRequest(){
       
    axios.post('/api/loans',{
        loanType:this.selectedLoan.name,
        amount:this.amount,
        payments:this.payments,
        destinationAccount:this.destinationAccount        

    })
    .then(res => console.log(res))
    .catch(err => console.log(err))
  },

  //alerts

    wrongLoan(){
        console.log(this.selectedLoan.name,this.destinationAccount,this.amount,this.payments) 
        if(this.selectedLoan.name == undefined && this.destinationAccount == "" && this.amount == "" && this.payments == ""){
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Please fill all the fields',       
                background:'#0e0747',
                color:'#ffffff'
              })
        }
        else if(!this.selectedLoan.name){
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Please choose a Loan',       
                background:'#0e0747',
                color:'#ffffff'
              })
        }
        else if(!this.destinationAccount){
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Please choose an account',       
                background:'#0e0747',
                color:'#ffffff'
              })
        }

        else if(this.amount < 5000.0){
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'The amount most be $5000 at least',       
                background:'#0e0747',
                color:'#ffffff'
              })
        }

        
        else if(this.amount > this.selectedLoan.maxAmount){
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: `The amount cant be greater than ${this.selectedLoan.maxAmount.toLocaleString('en-US')}` ,       
                background:'#0e0747',
                color:'#ffffff'
              })
        } 
        else if(!this.payments){
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Please choose your payments',       
                background:'#0e0747',
                color:'#ffffff'
              })
        }else{
            this.isTheclientSure()
        }

        },

        
    },

    computed: {
      loanTotal(){
        const loanAmount = parseFloat(this.amount)
        const total = loanAmount + (loanAmount * 0.2);
        this.interests = loanAmount *0.2
        return total.toLocaleString('en-US');
        
      }
    }


  })
  .mount('#app')
  