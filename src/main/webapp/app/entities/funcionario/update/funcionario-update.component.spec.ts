import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/service/pessoa.service';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
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
  let pessoaService: PessoaService;
  let empresaService: EmpresaService;

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
    pessoaService = TestBed.inject(PessoaService);
    empresaService = TestBed.inject(EmpresaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call pessoa query and add missing value', () => {
      const funcionario: IFuncionario = { id: 456 };
      const pessoa: IPessoa = { id: 6004 };
      funcionario.pessoa = pessoa;

      const pessoaCollection: IPessoa[] = [{ id: 1025 }];
      jest.spyOn(pessoaService, 'query').mockReturnValue(of(new HttpResponse({ body: pessoaCollection })));
      const expectedCollection: IPessoa[] = [pessoa, ...pessoaCollection];
      jest.spyOn(pessoaService, 'addPessoaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ funcionario });
      comp.ngOnInit();

      expect(pessoaService.query).toHaveBeenCalled();
      expect(pessoaService.addPessoaToCollectionIfMissing).toHaveBeenCalledWith(pessoaCollection, pessoa);
      expect(comp.pessoasCollection).toEqual(expectedCollection);
    });

    it('Should call Empresa query and add missing value', () => {
      const funcionario: IFuncionario = { id: 456 };
      const empresa: IEmpresa = { id: 4254 };
      funcionario.empresa = empresa;

      const empresaCollection: IEmpresa[] = [{ id: 9156 }];
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

    it('Should update editForm', () => {
      const funcionario: IFuncionario = { id: 456 };
      const pessoa: IPessoa = { id: 23594 };
      funcionario.pessoa = pessoa;
      const empresa: IEmpresa = { id: 26494 };
      funcionario.empresa = empresa;

      activatedRoute.data = of({ funcionario });
      comp.ngOnInit();

      expect(comp.pessoasCollection).toContain(pessoa);
      expect(comp.empresasSharedCollection).toContain(empresa);
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
  });
});
