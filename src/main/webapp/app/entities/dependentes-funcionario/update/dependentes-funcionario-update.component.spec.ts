import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/service/pessoa.service';
import { IFuncionario } from 'app/entities/funcionario/funcionario.model';
import { FuncionarioService } from 'app/entities/funcionario/service/funcionario.service';
import { IDependentesFuncionario } from '../dependentes-funcionario.model';
import { DependentesFuncionarioService } from '../service/dependentes-funcionario.service';
import { DependentesFuncionarioFormService } from './dependentes-funcionario-form.service';

import { DependentesFuncionarioUpdateComponent } from './dependentes-funcionario-update.component';

describe('DependentesFuncionario Management Update Component', () => {
  let comp: DependentesFuncionarioUpdateComponent;
  let fixture: ComponentFixture<DependentesFuncionarioUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let dependentesFuncionarioFormService: DependentesFuncionarioFormService;
  let dependentesFuncionarioService: DependentesFuncionarioService;
  let pessoaService: PessoaService;
  let funcionarioService: FuncionarioService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DependentesFuncionarioUpdateComponent],
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
      .overrideTemplate(DependentesFuncionarioUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DependentesFuncionarioUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    dependentesFuncionarioFormService = TestBed.inject(DependentesFuncionarioFormService);
    dependentesFuncionarioService = TestBed.inject(DependentesFuncionarioService);
    pessoaService = TestBed.inject(PessoaService);
    funcionarioService = TestBed.inject(FuncionarioService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Pessoa query and add missing value', () => {
      const dependentesFuncionario: IDependentesFuncionario = { id: 456 };
      const pessoa: IPessoa = { id: 15834 };
      dependentesFuncionario.pessoa = pessoa;

      const pessoaCollection: IPessoa[] = [{ id: 24184 }];
      jest.spyOn(pessoaService, 'query').mockReturnValue(of(new HttpResponse({ body: pessoaCollection })));
      const additionalPessoas = [pessoa];
      const expectedCollection: IPessoa[] = [...additionalPessoas, ...pessoaCollection];
      jest.spyOn(pessoaService, 'addPessoaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dependentesFuncionario });
      comp.ngOnInit();

      expect(pessoaService.query).toHaveBeenCalled();
      expect(pessoaService.addPessoaToCollectionIfMissing).toHaveBeenCalledWith(
        pessoaCollection,
        ...additionalPessoas.map(expect.objectContaining),
      );
      expect(comp.pessoasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Funcionario query and add missing value', () => {
      const dependentesFuncionario: IDependentesFuncionario = { id: 456 };
      const funcionario: IFuncionario = { id: 17510 };
      dependentesFuncionario.funcionario = funcionario;

      const funcionarioCollection: IFuncionario[] = [{ id: 15377 }];
      jest.spyOn(funcionarioService, 'query').mockReturnValue(of(new HttpResponse({ body: funcionarioCollection })));
      const additionalFuncionarios = [funcionario];
      const expectedCollection: IFuncionario[] = [...additionalFuncionarios, ...funcionarioCollection];
      jest.spyOn(funcionarioService, 'addFuncionarioToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dependentesFuncionario });
      comp.ngOnInit();

      expect(funcionarioService.query).toHaveBeenCalled();
      expect(funcionarioService.addFuncionarioToCollectionIfMissing).toHaveBeenCalledWith(
        funcionarioCollection,
        ...additionalFuncionarios.map(expect.objectContaining),
      );
      expect(comp.funcionariosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const dependentesFuncionario: IDependentesFuncionario = { id: 456 };
      const pessoa: IPessoa = { id: 26923 };
      dependentesFuncionario.pessoa = pessoa;
      const funcionario: IFuncionario = { id: 10701 };
      dependentesFuncionario.funcionario = funcionario;

      activatedRoute.data = of({ dependentesFuncionario });
      comp.ngOnInit();

      expect(comp.pessoasSharedCollection).toContain(pessoa);
      expect(comp.funcionariosSharedCollection).toContain(funcionario);
      expect(comp.dependentesFuncionario).toEqual(dependentesFuncionario);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDependentesFuncionario>>();
      const dependentesFuncionario = { id: 123 };
      jest.spyOn(dependentesFuncionarioFormService, 'getDependentesFuncionario').mockReturnValue(dependentesFuncionario);
      jest.spyOn(dependentesFuncionarioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dependentesFuncionario });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dependentesFuncionario }));
      saveSubject.complete();

      // THEN
      expect(dependentesFuncionarioFormService.getDependentesFuncionario).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(dependentesFuncionarioService.update).toHaveBeenCalledWith(expect.objectContaining(dependentesFuncionario));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDependentesFuncionario>>();
      const dependentesFuncionario = { id: 123 };
      jest.spyOn(dependentesFuncionarioFormService, 'getDependentesFuncionario').mockReturnValue({ id: null });
      jest.spyOn(dependentesFuncionarioService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dependentesFuncionario: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dependentesFuncionario }));
      saveSubject.complete();

      // THEN
      expect(dependentesFuncionarioFormService.getDependentesFuncionario).toHaveBeenCalled();
      expect(dependentesFuncionarioService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDependentesFuncionario>>();
      const dependentesFuncionario = { id: 123 };
      jest.spyOn(dependentesFuncionarioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dependentesFuncionario });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(dependentesFuncionarioService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePessoa', () => {
      it('Should forward to pessoaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(pessoaService, 'comparePessoa');
        comp.comparePessoa(entity, entity2);
        expect(pessoaService.comparePessoa).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareFuncionario', () => {
      it('Should forward to funcionarioService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(funcionarioService, 'compareFuncionario');
        comp.compareFuncionario(entity, entity2);
        expect(funcionarioService.compareFuncionario).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
