package com.revature.models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reimbursements")
public class Reimbursement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "author")
	private JoinUser author;

	private int amount;
	private String description;

	@ManyToOne
	@JoinColumn(name = "resolver")
	private JoinUser resolver;

	@ManyToOne
	@JoinColumn(name = "status", nullable = false)
	private ReimbursementStatus status;

	@ManyToOne
	@JoinColumn(name = "type", nullable = false)
	private ReimbursementType type;

	@Column(name = "date_resolved")
	private Date dateResolved;

	@Column(name = "date_submitted", nullable = false)
	private Date dateSubmitted;

	public Reimbursement() {
		super();
	}

	public Reimbursement(int id, JoinUser author, int amount, String description, JoinUser resolver,
			ReimbursementStatus status, ReimbursementType type, Date dateResolved, Date dateSubmitted) {
		super();
		this.id = id;
		this.author = author;
		this.amount = amount;
		this.description = description;
		this.resolver = resolver;
		this.status = status;
		this.type = type;
		this.dateResolved = dateResolved;
		this.dateSubmitted = dateSubmitted;
	}

	@Override
	public String toString() {
		return "Reimbursement [id=" + id + ", author=" + author + ", amount=" + amount + ", description=" + description
				+ ", resolver=" + resolver + ", status=" + status + ", type=" + type + ", dateResolved=" + dateResolved
				+ ", dateSubmitted=" + dateSubmitted + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((dateResolved == null) ? 0 : dateResolved.hashCode());
		result = prime * result + ((dateSubmitted == null) ? 0 : dateSubmitted.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((resolver == null) ? 0 : resolver.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reimbursement other = (Reimbursement) obj;
		if (amount != other.amount)
			return false;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (dateResolved == null) {
			if (other.dateResolved != null)
				return false;
		} else if (!dateResolved.equals(other.dateResolved))
			return false;
		if (dateSubmitted == null) {
			if (other.dateSubmitted != null)
				return false;
		} else if (!dateSubmitted.equals(other.dateSubmitted))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (resolver == null) {
			if (other.resolver != null)
				return false;
		} else if (!resolver.equals(other.resolver))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public JoinUser getAuthor() {
		return author;
	}

	public void setAuthor(JoinUser author) {
		this.author = author;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public JoinUser getResolver() {
		return resolver;
	}

	public void setResolver(JoinUser resolver) {
		this.resolver = resolver;
	}

	public ReimbursementStatus getStatus() {
		return status;
	}

	public void setStatus(ReimbursementStatus status) {
		this.status = status;
	}

	public ReimbursementType getType() {
		return type;
	}

	public void setType(ReimbursementType type) {
		this.type = type;
	}

	public Date getDateResolved() {
		return dateResolved;
	}

	public void setDateResolved(Date dateResolved) {
		this.dateResolved = dateResolved;
	}

	public Date getDateSubmitted() {
		return dateSubmitted;
	}

	public void setDateSubmitted(Date dateSubmitted) {
		this.dateSubmitted = dateSubmitted;
	}

}
