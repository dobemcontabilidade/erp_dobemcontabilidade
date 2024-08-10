import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/service/pessoa.service';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { IUsuarioEmpresa } from '../usuario-empresa.model';
import { UsuarioEmpresaService } from '../service/usuario-empresa.service';
import { UsuarioEmpresaFormService } from './usuario-empresa-form.service';

import { UsuarioEmpresaUpdateComponent } from './usuario-empresa-update.component';

describe('UsuarioEmpresa Management Update Component', () => {
  let comp: UsuarioEmpresaUpdateComponent;
  let fixture: ComponentFixture<UsuarioEmpresaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let usuarioEmpresaFormService: UsuarioEmpresaFormService;
  let usuarioEmpresaService: UsuarioEmpresaService;
  let pessoaService: PessoaService;
  let empresaService: EmpresaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [UsuarioEmpresaUpdateComponent],
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
      .overrideTemplate(UsuarioEmpresaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UsuarioEmpresaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    usuarioEmpresaFormService = TestBed.inject(UsuarioEmpresaFormService);
    usuarioEmpresaService = TestBed.inject(UsuarioEmpresaService);
    pessoaService = TestBed.inject(PessoaService);
    empresaService = TestBed.inject(EmpresaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call pessoa query and add missing value', () => {
      const usuarioEmpresa: IUsuarioEmpresa = { id: 456 };
      const pessoa: IPessoa = { id: 12954 };
      usuarioEmpresa.pessoa = pessoa;

      const pessoaCollection: IPessoa[] = [{ id: 22411 }];
      jest.spyOn(pessoaService, 'query').mockReturnValue(of(new HttpResponse({ body: pessoaCollection })));
      const expectedCollection: IPessoa[] = [pessoa, ...pessoaCollection];
      jest.spyOn(pessoaService, 'addPessoaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ usuarioEmpresa });
      comp.ngOnInit();

      expect(pessoaService.query).toHaveBeenCalled();
      expect(pessoaService.addPessoaToCollectionIfMissing).toHaveBeenCalledWith(pessoaCollection, pessoa);
      expect(comp.pessoasCollection).toEqual(expectedCollection);
    });

    it('Should call Empresa query and add missing value', () => {
      const usuarioEmpresa: IUsuarioEmpresa = { id: 456 };
      const empresa: IEmpresa = { id: 28437 };
      usuarioEmpresa.empresa = empresa;

      const empresaCollection: IEmpresa[] = [{ id: 27183 }];
      jest.spyOn(empresaService, 'query').mockReturnValue(of(new HttpResponse({ body: empresaCollection })));
      const additionalEmpresas = [empresa];
      const expectedCollection: IEmpresa[] = [...additionalEmpresas, ...empresaCollection];
      jest.spyOn(empresaService, 'addEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ usuarioEmpresa });
      comp.ngOnInit();

      expect(empresaService.query).toHaveBeenCalled();
      expect(empresaService.addEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        empresaCollection,
        ...additionalEmpresas.map(expect.objectContaining),
      );
      expect(comp.empresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const usuarioEmpresa: IUsuarioEmpresa = { id: 456 };
      const pessoa: IPessoa = { id: 10797 };
      usuarioEmpresa.pessoa = pessoa;
      const empresa: IEmpresa = { id: 32064 };
      usuarioEmpresa.empresa = empresa;

      activatedRoute.data = of({ usuarioEmpresa });
      comp.ngOnInit();

      expect(comp.pessoasCollection).toContain(pessoa);
      expect(comp.empresasSharedCollection).toContain(empresa);
      expect(comp.usuarioEmpresa).toEqual(usuarioEmpresa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUsuarioEmpresa>>();
      const usuarioEmpresa = { id: 123 };
      jest.spyOn(usuarioEmpresaFormService, 'getUsuarioEmpresa').mockReturnValue(usuarioEmpresa);
      jest.spyOn(usuarioEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ usuarioEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: usuarioEmpresa }));
      saveSubject.complete();

      // THEN
      expect(usuarioEmpresaFormService.getUsuarioEmpresa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(usuarioEmpresaService.update).toHaveBeenCalledWith(expect.objectContaining(usuarioEmpresa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUsuarioEmpresa>>();
      const usuarioEmpresa = { id: 123 };
      jest.spyOn(usuarioEmpresaFormService, 'getUsuarioEmpresa').mockReturnValue({ id: null });
      jest.spyOn(usuarioEmpresaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ usuarioEmpresa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: usuarioEmpresa }));
      saveSubject.complete();

      // THEN
      expect(usuarioEmpresaFormService.getUsuarioEmpresa).toHaveBeenCalled();
      expect(usuarioEmpresaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUsuarioEmpresa>>();
      const usuarioEmpresa = { id: 123 };
      jest.spyOn(usuarioEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ usuarioEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(usuarioEmpresaService.update).toHaveBeenCalled();
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
