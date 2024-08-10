import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/service/pessoa.service';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { ISocio } from '../socio.model';
import { SocioService } from '../service/socio.service';
import { SocioFormService } from './socio-form.service';

import { SocioUpdateComponent } from './socio-update.component';

describe('Socio Management Update Component', () => {
  let comp: SocioUpdateComponent;
  let fixture: ComponentFixture<SocioUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let socioFormService: SocioFormService;
  let socioService: SocioService;
  let pessoaService: PessoaService;
  let empresaService: EmpresaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SocioUpdateComponent],
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
      .overrideTemplate(SocioUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SocioUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    socioFormService = TestBed.inject(SocioFormService);
    socioService = TestBed.inject(SocioService);
    pessoaService = TestBed.inject(PessoaService);
    empresaService = TestBed.inject(EmpresaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call pessoa query and add missing value', () => {
      const socio: ISocio = { id: 456 };
      const pessoa: IPessoa = { id: 26682 };
      socio.pessoa = pessoa;

      const pessoaCollection: IPessoa[] = [{ id: 16899 }];
      jest.spyOn(pessoaService, 'query').mockReturnValue(of(new HttpResponse({ body: pessoaCollection })));
      const expectedCollection: IPessoa[] = [pessoa, ...pessoaCollection];
      jest.spyOn(pessoaService, 'addPessoaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ socio });
      comp.ngOnInit();

      expect(pessoaService.query).toHaveBeenCalled();
      expect(pessoaService.addPessoaToCollectionIfMissing).toHaveBeenCalledWith(pessoaCollection, pessoa);
      expect(comp.pessoasCollection).toEqual(expectedCollection);
    });

    it('Should call Empresa query and add missing value', () => {
      const socio: ISocio = { id: 456 };
      const empresa: IEmpresa = { id: 303 };
      socio.empresa = empresa;

      const empresaCollection: IEmpresa[] = [{ id: 4254 }];
      jest.spyOn(empresaService, 'query').mockReturnValue(of(new HttpResponse({ body: empresaCollection })));
      const additionalEmpresas = [empresa];
      const expectedCollection: IEmpresa[] = [...additionalEmpresas, ...empresaCollection];
      jest.spyOn(empresaService, 'addEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ socio });
      comp.ngOnInit();

      expect(empresaService.query).toHaveBeenCalled();
      expect(empresaService.addEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        empresaCollection,
        ...additionalEmpresas.map(expect.objectContaining),
      );
      expect(comp.empresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const socio: ISocio = { id: 456 };
      const pessoa: IPessoa = { id: 24369 };
      socio.pessoa = pessoa;
      const empresa: IEmpresa = { id: 9156 };
      socio.empresa = empresa;

      activatedRoute.data = of({ socio });
      comp.ngOnInit();

      expect(comp.pessoasCollection).toContain(pessoa);
      expect(comp.empresasSharedCollection).toContain(empresa);
      expect(comp.socio).toEqual(socio);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISocio>>();
      const socio = { id: 123 };
      jest.spyOn(socioFormService, 'getSocio').mockReturnValue(socio);
      jest.spyOn(socioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ socio });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: socio }));
      saveSubject.complete();

      // THEN
      expect(socioFormService.getSocio).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(socioService.update).toHaveBeenCalledWith(expect.objectContaining(socio));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISocio>>();
      const socio = { id: 123 };
      jest.spyOn(socioFormService, 'getSocio').mockReturnValue({ id: null });
      jest.spyOn(socioService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ socio: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: socio }));
      saveSubject.complete();

      // THEN
      expect(socioFormService.getSocio).toHaveBeenCalled();
      expect(socioService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISocio>>();
      const socio = { id: 123 };
      jest.spyOn(socioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ socio });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(socioService.update).toHaveBeenCalled();
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
