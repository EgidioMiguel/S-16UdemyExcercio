package services;

import java.time.LocalDate;

import entities.Contract;
import entities.Installment;

public class ContractService {
	
	private OnlinePaymentService ops;

	public ContractService(OnlinePaymentService ops) {
		this.ops = ops;
	}	
	
	public void  processContract(Contract contract,int months) {
		
		double basicQuota = contract.getTotalValue()/months;
		
		for(int i=1;i<=months;i++) {
			
			LocalDate dueDate = contract.getDate().plusMonths(i);			
			double interest = ops.interest(basicQuota, i);
			double fee = ops.paymentFee(basicQuota+interest);
			double quota =  basicQuota+ interest+ fee;
			
			contract.getInstallment().add(new Installment(dueDate, quota));
		}
	}		
	
}
