package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.EstadoCivilEnum;
import com.dobemcontabilidade.domain.enumeration.PessoaComDeficienciaEnum;
import com.dobemcontabilidade.domain.enumeration.RacaECorEnum;
import com.dobemcontabilidade.domain.enumeration.SexoEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Pessoa.
 */
@Entity
@Table(name = "pessoa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "nome", length = 200, nullable = false)
    private String nome;

    @NotNull
    @Size(max = 200)
    @Column(name = "cpf", length = 200, nullable = false)
    private String cpf;

    @Size(max = 15)
    @Column(name = "data_nascimento", length = 15)
    private String dataNascimento;

    @Column(name = "titulo_eleitor")
    private Integer tituloEleitor;

    @NotNull
    @Size(max = 10)
    @Column(name = "rg", length = 10, nullable = false)
    private String rg;

    @Column(name = "rg_orgao_expeditor")
    private String rgOrgaoExpeditor;

    @Column(name = "rg_uf_expedicao")
    private String rgUfExpedicao;

    @Size(max = 200)
    @Column(name = "nome_mae", length = 200)
    private String nomeMae;

    @Size(max = 200)
    @Column(name = "nome_pai", length = 200)
    private String nomePai;

    @Column(name = "local_nascimento")
    private String localNascimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "raca_e_cor")
    private RacaECorEnum racaECor;

    @Enumerated(EnumType.STRING)
    @Column(name = "pessoa_com_deficiencia")
    private PessoaComDeficienciaEnum pessoaComDeficiencia;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_civil")
    private EstadoCivilEnum estadoCivil;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", nullable = false)
    private SexoEnum sexo;

    @Column(name = "url_foto_perfil")
    private String urlFotoPerfil;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pessoa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "usuarioEmpresa",
            "estrangeiros",
            "contratoFuncionarios",
            "demissaoFuncionarios",
            "dependentesFuncionarios",
            "empresaVinculadas",
            "departamentoFuncionarios",
            "pessoa",
            "empresa",
            "profissao",
        },
        allowSetters = true
    )
    private Set<Funcionario> funcionarios = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pessoa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "anexoRequeridoPessoas", "pessoa" }, allowSetters = true)
    private Set<AnexoPessoa> anexoPessoas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pessoa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pessoa", "escolaridade" }, allowSetters = true)
    private Set<EscolaridadePessoa> escolaridadePessoas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pessoa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pessoa", "banco" }, allowSetters = true)
    private Set<BancoPessoa> bancoPessoas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pessoa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pessoa", "funcionario" }, allowSetters = true)
    private Set<DependentesFuncionario> dependentesFuncionarios = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pessoa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pessoa", "cidade" }, allowSetters = true)
    private Set<EnderecoPessoa> enderecoPessoas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pessoa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pessoa" }, allowSetters = true)
    private Set<Email> emails = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pessoa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pessoa" }, allowSetters = true)
    private Set<Telefone> telefones = new HashSet<>();

    @JsonIgnoreProperties(value = { "pessoa", "usuarioGestao" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "pessoa")
    private Administrador administrador;

    @JsonIgnoreProperties(
        value = {
            "pessoa",
            "usuarioContador",
            "areaContabilAssinaturaEmpresas",
            "feedBackContadorParaUsuarios",
            "ordemServicos",
            "areaContabilContadors",
            "contadorResponsavelOrdemServicos",
            "contadorResponsavelTarefaRecorrentes",
            "departamentoEmpresas",
            "departamentoContadors",
            "termoAdesaoContadors",
            "avaliacaoContadors",
            "tarefaEmpresas",
            "perfilContador",
        },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "pessoa")
    private Contador contador;

    @JsonIgnoreProperties(value = { "pessoa", "usuarioEmpresa", "empresa", "profissao" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "pessoa")
    private Socio socio;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Pessoa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Pessoa nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return this.cpf;
    }

    public Pessoa cpf(String cpf) {
        this.setCpf(cpf);
        return this;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataNascimento() {
        return this.dataNascimento;
    }

    public Pessoa dataNascimento(String dataNascimento) {
        this.setDataNascimento(dataNascimento);
        return this;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Integer getTituloEleitor() {
        return this.tituloEleitor;
    }

    public Pessoa tituloEleitor(Integer tituloEleitor) {
        this.setTituloEleitor(tituloEleitor);
        return this;
    }

    public void setTituloEleitor(Integer tituloEleitor) {
        this.tituloEleitor = tituloEleitor;
    }

    public String getRg() {
        return this.rg;
    }

    public Pessoa rg(String rg) {
        this.setRg(rg);
        return this;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getRgOrgaoExpeditor() {
        return this.rgOrgaoExpeditor;
    }

    public Pessoa rgOrgaoExpeditor(String rgOrgaoExpeditor) {
        this.setRgOrgaoExpeditor(rgOrgaoExpeditor);
        return this;
    }

    public void setRgOrgaoExpeditor(String rgOrgaoExpeditor) {
        this.rgOrgaoExpeditor = rgOrgaoExpeditor;
    }

    public String getRgUfExpedicao() {
        return this.rgUfExpedicao;
    }

    public Pessoa rgUfExpedicao(String rgUfExpedicao) {
        this.setRgUfExpedicao(rgUfExpedicao);
        return this;
    }

    public void setRgUfExpedicao(String rgUfExpedicao) {
        this.rgUfExpedicao = rgUfExpedicao;
    }

    public String getNomeMae() {
        return this.nomeMae;
    }

    public Pessoa nomeMae(String nomeMae) {
        this.setNomeMae(nomeMae);
        return this;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public String getNomePai() {
        return this.nomePai;
    }

    public Pessoa nomePai(String nomePai) {
        this.setNomePai(nomePai);
        return this;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public String getLocalNascimento() {
        return this.localNascimento;
    }

    public Pessoa localNascimento(String localNascimento) {
        this.setLocalNascimento(localNascimento);
        return this;
    }

    public void setLocalNascimento(String localNascimento) {
        this.localNascimento = localNascimento;
    }

    public RacaECorEnum getRacaECor() {
        return this.racaECor;
    }

    public Pessoa racaECor(RacaECorEnum racaECor) {
        this.setRacaECor(racaECor);
        return this;
    }

    public void setRacaECor(RacaECorEnum racaECor) {
        this.racaECor = racaECor;
    }

    public PessoaComDeficienciaEnum getPessoaComDeficiencia() {
        return this.pessoaComDeficiencia;
    }

    public Pessoa pessoaComDeficiencia(PessoaComDeficienciaEnum pessoaComDeficiencia) {
        this.setPessoaComDeficiencia(pessoaComDeficiencia);
        return this;
    }

    public void setPessoaComDeficiencia(PessoaComDeficienciaEnum pessoaComDeficiencia) {
        this.pessoaComDeficiencia = pessoaComDeficiencia;
    }

    public EstadoCivilEnum getEstadoCivil() {
        return this.estadoCivil;
    }

    public Pessoa estadoCivil(EstadoCivilEnum estadoCivil) {
        this.setEstadoCivil(estadoCivil);
        return this;
    }

    public void setEstadoCivil(EstadoCivilEnum estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public SexoEnum getSexo() {
        return this.sexo;
    }

    public Pessoa sexo(SexoEnum sexo) {
        this.setSexo(sexo);
        return this;
    }

    public void setSexo(SexoEnum sexo) {
        this.sexo = sexo;
    }

    public String getUrlFotoPerfil() {
        return this.urlFotoPerfil;
    }

    public Pessoa urlFotoPerfil(String urlFotoPerfil) {
        this.setUrlFotoPerfil(urlFotoPerfil);
        return this;
    }

    public void setUrlFotoPerfil(String urlFotoPerfil) {
        this.urlFotoPerfil = urlFotoPerfil;
    }

    public Set<Funcionario> getFuncionarios() {
        return this.funcionarios;
    }

    public void setFuncionarios(Set<Funcionario> funcionarios) {
        if (this.funcionarios != null) {
            this.funcionarios.forEach(i -> i.setPessoa(null));
        }
        if (funcionarios != null) {
            funcionarios.forEach(i -> i.setPessoa(this));
        }
        this.funcionarios = funcionarios;
    }

    public Pessoa funcionarios(Set<Funcionario> funcionarios) {
        this.setFuncionarios(funcionarios);
        return this;
    }

    public Pessoa addFuncionario(Funcionario funcionario) {
        this.funcionarios.add(funcionario);
        funcionario.setPessoa(this);
        return this;
    }

    public Pessoa removeFuncionario(Funcionario funcionario) {
        this.funcionarios.remove(funcionario);
        funcionario.setPessoa(null);
        return this;
    }

    public Set<AnexoPessoa> getAnexoPessoas() {
        return this.anexoPessoas;
    }

    public void setAnexoPessoas(Set<AnexoPessoa> anexoPessoas) {
        if (this.anexoPessoas != null) {
            this.anexoPessoas.forEach(i -> i.setPessoa(null));
        }
        if (anexoPessoas != null) {
            anexoPessoas.forEach(i -> i.setPessoa(this));
        }
        this.anexoPessoas = anexoPessoas;
    }

    public Pessoa anexoPessoas(Set<AnexoPessoa> anexoPessoas) {
        this.setAnexoPessoas(anexoPessoas);
        return this;
    }

    public Pessoa addAnexoPessoa(AnexoPessoa anexoPessoa) {
        this.anexoPessoas.add(anexoPessoa);
        anexoPessoa.setPessoa(this);
        return this;
    }

    public Pessoa removeAnexoPessoa(AnexoPessoa anexoPessoa) {
        this.anexoPessoas.remove(anexoPessoa);
        anexoPessoa.setPessoa(null);
        return this;
    }

    public Set<EscolaridadePessoa> getEscolaridadePessoas() {
        return this.escolaridadePessoas;
    }

    public void setEscolaridadePessoas(Set<EscolaridadePessoa> escolaridadePessoas) {
        if (this.escolaridadePessoas != null) {
            this.escolaridadePessoas.forEach(i -> i.setPessoa(null));
        }
        if (escolaridadePessoas != null) {
            escolaridadePessoas.forEach(i -> i.setPessoa(this));
        }
        this.escolaridadePessoas = escolaridadePessoas;
    }

    public Pessoa escolaridadePessoas(Set<EscolaridadePessoa> escolaridadePessoas) {
        this.setEscolaridadePessoas(escolaridadePessoas);
        return this;
    }

    public Pessoa addEscolaridadePessoa(EscolaridadePessoa escolaridadePessoa) {
        this.escolaridadePessoas.add(escolaridadePessoa);
        escolaridadePessoa.setPessoa(this);
        return this;
    }

    public Pessoa removeEscolaridadePessoa(EscolaridadePessoa escolaridadePessoa) {
        this.escolaridadePessoas.remove(escolaridadePessoa);
        escolaridadePessoa.setPessoa(null);
        return this;
    }

    public Set<BancoPessoa> getBancoPessoas() {
        return this.bancoPessoas;
    }

    public void setBancoPessoas(Set<BancoPessoa> bancoPessoas) {
        if (this.bancoPessoas != null) {
            this.bancoPessoas.forEach(i -> i.setPessoa(null));
        }
        if (bancoPessoas != null) {
            bancoPessoas.forEach(i -> i.setPessoa(this));
        }
        this.bancoPessoas = bancoPessoas;
    }

    public Pessoa bancoPessoas(Set<BancoPessoa> bancoPessoas) {
        this.setBancoPessoas(bancoPessoas);
        return this;
    }

    public Pessoa addBancoPessoa(BancoPessoa bancoPessoa) {
        this.bancoPessoas.add(bancoPessoa);
        bancoPessoa.setPessoa(this);
        return this;
    }

    public Pessoa removeBancoPessoa(BancoPessoa bancoPessoa) {
        this.bancoPessoas.remove(bancoPessoa);
        bancoPessoa.setPessoa(null);
        return this;
    }

    public Set<DependentesFuncionario> getDependentesFuncionarios() {
        return this.dependentesFuncionarios;
    }

    public void setDependentesFuncionarios(Set<DependentesFuncionario> dependentesFuncionarios) {
        if (this.dependentesFuncionarios != null) {
            this.dependentesFuncionarios.forEach(i -> i.setPessoa(null));
        }
        if (dependentesFuncionarios != null) {
            dependentesFuncionarios.forEach(i -> i.setPessoa(this));
        }
        this.dependentesFuncionarios = dependentesFuncionarios;
    }

    public Pessoa dependentesFuncionarios(Set<DependentesFuncionario> dependentesFuncionarios) {
        this.setDependentesFuncionarios(dependentesFuncionarios);
        return this;
    }

    public Pessoa addDependentesFuncionario(DependentesFuncionario dependentesFuncionario) {
        this.dependentesFuncionarios.add(dependentesFuncionario);
        dependentesFuncionario.setPessoa(this);
        return this;
    }

    public Pessoa removeDependentesFuncionario(DependentesFuncionario dependentesFuncionario) {
        this.dependentesFuncionarios.remove(dependentesFuncionario);
        dependentesFuncionario.setPessoa(null);
        return this;
    }

    public Set<EnderecoPessoa> getEnderecoPessoas() {
        return this.enderecoPessoas;
    }

    public void setEnderecoPessoas(Set<EnderecoPessoa> enderecoPessoas) {
        if (this.enderecoPessoas != null) {
            this.enderecoPessoas.forEach(i -> i.setPessoa(null));
        }
        if (enderecoPessoas != null) {
            enderecoPessoas.forEach(i -> i.setPessoa(this));
        }
        this.enderecoPessoas = enderecoPessoas;
    }

    public Pessoa enderecoPessoas(Set<EnderecoPessoa> enderecoPessoas) {
        this.setEnderecoPessoas(enderecoPessoas);
        return this;
    }

    public Pessoa addEnderecoPessoa(EnderecoPessoa enderecoPessoa) {
        this.enderecoPessoas.add(enderecoPessoa);
        enderecoPessoa.setPessoa(this);
        return this;
    }

    public Pessoa removeEnderecoPessoa(EnderecoPessoa enderecoPessoa) {
        this.enderecoPessoas.remove(enderecoPessoa);
        enderecoPessoa.setPessoa(null);
        return this;
    }

    public Set<Email> getEmails() {
        return this.emails;
    }

    public void setEmails(Set<Email> emails) {
        if (this.emails != null) {
            this.emails.forEach(i -> i.setPessoa(null));
        }
        if (emails != null) {
            emails.forEach(i -> i.setPessoa(this));
        }
        this.emails = emails;
    }

    public Pessoa emails(Set<Email> emails) {
        this.setEmails(emails);
        return this;
    }

    public Pessoa addEmail(Email email) {
        this.emails.add(email);
        email.setPessoa(this);
        return this;
    }

    public Pessoa removeEmail(Email email) {
        this.emails.remove(email);
        email.setPessoa(null);
        return this;
    }

    public Set<Telefone> getTelefones() {
        return this.telefones;
    }

    public void setTelefones(Set<Telefone> telefones) {
        if (this.telefones != null) {
            this.telefones.forEach(i -> i.setPessoa(null));
        }
        if (telefones != null) {
            telefones.forEach(i -> i.setPessoa(this));
        }
        this.telefones = telefones;
    }

    public Pessoa telefones(Set<Telefone> telefones) {
        this.setTelefones(telefones);
        return this;
    }

    public Pessoa addTelefone(Telefone telefone) {
        this.telefones.add(telefone);
        telefone.setPessoa(this);
        return this;
    }

    public Pessoa removeTelefone(Telefone telefone) {
        this.telefones.remove(telefone);
        telefone.setPessoa(null);
        return this;
    }

    public Administrador getAdministrador() {
        return this.administrador;
    }

    public void setAdministrador(Administrador administrador) {
        if (this.administrador != null) {
            this.administrador.setPessoa(null);
        }
        if (administrador != null) {
            administrador.setPessoa(this);
        }
        this.administrador = administrador;
    }

    public Pessoa administrador(Administrador administrador) {
        this.setAdministrador(administrador);
        return this;
    }

    public Contador getContador() {
        return this.contador;
    }

    public void setContador(Contador contador) {
        if (this.contador != null) {
            this.contador.setPessoa(null);
        }
        if (contador != null) {
            contador.setPessoa(this);
        }
        this.contador = contador;
    }

    public Pessoa contador(Contador contador) {
        this.setContador(contador);
        return this;
    }

    public Socio getSocio() {
        return this.socio;
    }

    public void setSocio(Socio socio) {
        if (this.socio != null) {
            this.socio.setPessoa(null);
        }
        if (socio != null) {
            socio.setPessoa(this);
        }
        this.socio = socio;
    }

    public Pessoa socio(Socio socio) {
        this.setSocio(socio);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pessoa)) {
            return false;
        }
        return getId() != null && getId().equals(((Pessoa) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pessoa{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", cpf='" + getCpf() + "'" +
            ", dataNascimento='" + getDataNascimento() + "'" +
            ", tituloEleitor=" + getTituloEleitor() +
            ", rg='" + getRg() + "'" +
            ", rgOrgaoExpeditor='" + getRgOrgaoExpeditor() + "'" +
            ", rgUfExpedicao='" + getRgUfExpedicao() + "'" +
            ", nomeMae='" + getNomeMae() + "'" +
            ", nomePai='" + getNomePai() + "'" +
            ", localNascimento='" + getLocalNascimento() + "'" +
            ", racaECor='" + getRacaECor() + "'" +
            ", pessoaComDeficiencia='" + getPessoaComDeficiencia() + "'" +
            ", estadoCivil='" + getEstadoCivil() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", urlFotoPerfil='" + getUrlFotoPerfil() + "'" +
            "}";
    }
}
