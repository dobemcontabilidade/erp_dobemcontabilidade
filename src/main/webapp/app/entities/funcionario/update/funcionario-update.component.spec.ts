import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IUsuarioEmpresa } from 'app/entities/usuario-empresa/usuario-empresa.model';
import { UsuarioEmpresaService } from 'app/entities/usuario-empresa/service/usuario-empresa.service';
import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/service/pessoa.service';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { IProfissao } from 'app/entities/profissao/profissao.model';
import { ProfissaoService } from 'app/entities/profissao/service/profissao.service';
import { IFuncionario } from '../funcionario.model';
import { FuncionarioService } from '../service/funcionario.service';
import { FuncionarioFormService } from './funcionario-form.service';

import { FuncionarioUpdateComponent } from './funcionario-update.component';

describe('Funcionario Management Update Component', () => {
  let comp: FuncionarioUpdateComponent;
  let fixture: ComponentFixture<FuncionarioUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let funcionarioFormService: FuncionarioFormService;
  let funcionarioService: FuncionarioService;
  let usuarioEmpresaService: UsuarioEmpresaService;
  let pessoaService: PessoaService;
  let empresaService: EmpresaService;
  let profissaoService: ProfissaoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FuncionarioUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(FuncionarioUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FuncionarioUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    funcionarioFormService = TestBed.inject(FuncionarioFormService);
    funcionarioService = TestBed.inject(FuncionarioService);
    usuarioEmpresaService = TestBed.inject(UsuarioEmpresaService);
    pessoaService = TestBed.inject(PessoaService);
    empresaService = TestBed.inject(EmpresaService);
    profissaoService = TestBed.inject(ProfissaoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call usuarioEmpresa query and add missing value', () => {
      const funcionario: IFuncionario = { id: 456 };
      const usuarioEmpresa: IUsuarioEmpresa = { id: 15052 };
      funcionario.usuarioEmpresa = usuarioEmpresa;

      const usuarioEmpresaCollection: IUsuarioEmpresa[] = [{ id: 27166 }];
      jest.spyOn(usuarioEmpresaService, 'query').mockReturnValue(of(new HttpResponse({ body: usuarioEmpresaCollection })));
      const expectedCollection: IUsuarioEmpresa[] = [usuarioEmpresa, ...usuarioEmpresaCollection];
      jest.spyOn(usuarioEmpresaService, 'addUsuarioEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ funcionario });
      comp.ngOnInit();

      expect(usuarioEmpresaService.query).toHaveBeenCalled();
      expect(usuarioEmpresaService.addUsuarioEmpresaToCollectionIfMissing).toHaveBeenCalledWith(usuarioEmpresaCollection, usuarioEmpresa);
      expect(comp.usuarioEmpresasCollection).toEqual(expectedCollection);
    });

    it('Should call Pessoa query and add missing value', () => {
      const funcionario: IFuncionario = { id: 456 };
      const pessoa: IPessoa = { id: 22577 };
      funcionario.pessoa = pessoa;

      const pessoaCollection: IPessoa[] = [{ id: 15693 }];
      jest.spyOn(pessoaService, 'query').mockReturnValue(of(new HttpResponse({ body: pessoaCollection })));
      const additionalPessoas = [pessoa];
      const expectedCollection: IPessoa[] = [...additionalPessoas, ...pessoaCollection];
      jest.spyOn(pessoaService, 'addPessoaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ funcionario });
      comp.ngOnInit();

      expect(pessoaService.query).toHaveBeenCalled();
      expect(pessoaService.addPessoaToCollectionIfMissing).toHaveBeenCalledWith(
        pessoaCollection,
        ...additionalPessoas.map(expect.objectContaining),
      );
      expect(comp.pessoasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Empresa query and add missing value', () => {
      const funcionario: IFuncionario = { id: 456 };
      const empresa: IEmpresa = { id: 2814 };
      funcionario.empresa = empresa;

      const empresaCollection: IEmpresa[] = [{ id: 22999 }];
      jest.spyOn(empresaService, 'query').mockReturnValue(of(new HttpResponse({ body: empresaCollection })));
      const additionalEmpresas = [empresa];
      const expectedCollection: IEmpresa[] = [...additionalEmpresas, ...empresaCollection];
      jest.spyOn(empresaService, 'addEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ funcionario });
      comp.ngOnInit();

      expect(empresaService.query).toHaveBeenCalled();
      expect(empresaService.addEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        empresaCollection,
        ...additionalEmpresas.map(expect.objectContaining),
      );
      expect(comp.empresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Profissao query and add missing value', () => {
      const funcionario: IFuncionario = { id: 456 };
      const profissao: IProfissao = { id: 14662 };
      funcionario.profissao = profissao;

      const profissaoCollection: IProfissao[] = [{ id: 9957 }];
      jest.spyOn(profissaoService, 'query').mockReturnValue(of(new HttpResponse({ body: profissaoCollection })));
      const additionalProfissaos = [profissao];
      const expectedCollection: IProfissao[] = [...additionalProfissaos, ...profissaoCollection];
      jest.spyOn(profissaoService, 'addProfissaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ funcionario });
      comp.ngOnInit();

      expect(profissaoService.query).toHaveBeenCalled();
      expect(profissaoService.addProfissaoToCollectionIfMissing).toHaveBeenCalledWith(
        profissaoCollection,
        ...additionalProfissaos.map(expect.objectContaining),
      );
      expect(comp.profissaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const funcionario: IFuncionario = { id: 456 };
      const usuarioEmpresa: IUsuarioEmpresa = { id: 25565 };
      funcionario.usuarioEmpresa = usuarioEmpresa;
      const pessoa: IPessoa = { id: 32753 };
      funcionario.pessoa = pessoa;
      const empresa: IEmpresa = { id: 1130 };
      funcionario.empresa = empresa;
      const profissao: IProfissao = { id: 26617 };
      funcionario.profissao = profissao;

      activatedRoute.data = of({ funcionario });
      comp.ngOnInit();

      expect(comp.usuarioEmpresasCollection).toContain(usuarioEmpresa);
      expect(comp.pessoasSharedCollection).toContain(pessoa);
      expect(comp.empresasSharedCollection).toContain(empresa);
      expect(comp.profissaosSharedCollection).toContain(profissao);
      expect(comp.funcionario).toEqual(funcionario);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuncionario>>();
      const funcionario = { id: 123 };
      jest.spyOn(funcionarioFormService, 'getFuncionario').mockReturnValue(funcionario);
      jest.spyOn(funcionarioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ funcionario });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: funcionario }));
      saveSubject.complete();

      // THEN
      expect(funcionarioFormService.getFuncionario).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(funcionarioService.update).toHaveBeenCalledWith(expect.objectContaining(funcionario));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuncionario>>();
      const funcionario = { id: 123 };
      jest.spyOn(funcionarioFormService, 'getFuncionario').mockReturnValue({ id: null });
      jest.spyOn(funcionarioService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ funcionario: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: funcionario }));
      saveSubject.complete();

      // THEN
      expect(funcionarioFormService.getFuncionario).toHaveBeenCalled();
      expect(funcionarioService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuncionario>>();
      const funcionario = { id: 123 };
      jest.spyOn(funcionarioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ funcionario });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(funcionarioService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareUsuarioEmpresa', () => {
      it('Should forward to usuarioEmpresaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(usuarioEmpresaService, 'compareUsuarioEmpresa');
        comp.compareUsuarioEmpresa(entity, entity2);
        expect(usuarioEmpresaService.compareUsuarioEmpresa).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePessoa', () => {
      it('Should forward to pessoaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(pessoaService, 'comparePessoa');
        comp.comparePessoa(entity, entity2);
        expect(pessoaService.comparePessoa).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareEmpresa', () => {
      it('Should forward to empresaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(empresaService, 'compareEmpresa');
        comp.compareEmpresa(entity, entity2);
        expect(empresaService.compareEmpresa).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareProfissao', () => {
      it('Should forward to profissaoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(profissaoService, 'compareProfissao');
        comp.compareProfissao(entity, entity2);
        expect(profissaoService.compareProfissao).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
