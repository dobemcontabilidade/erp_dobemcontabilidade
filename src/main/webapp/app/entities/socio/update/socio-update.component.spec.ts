import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IPessoaFisica } from 'app/entities/pessoa-fisica/pessoa-fisica.model';
import { PessoaFisicaService } from 'app/entities/pessoa-fisica/service/pessoa-fisica.service';
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
  let pessoaFisicaService: PessoaFisicaService;
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
    pessoaFisicaService = TestBed.inject(PessoaFisicaService);
    empresaService = TestBed.inject(EmpresaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call pessoaFisica query and add missing value', () => {
      const socio: ISocio = { id: 456 };
      const pessoaFisica: IPessoaFisica = { id: 11238 };
      socio.pessoaFisica = pessoaFisica;

      const pessoaFisicaCollection: IPessoaFisica[] = [{ id: 21723 }];
      jest.spyOn(pessoaFisicaService, 'query').mockReturnValue(of(new HttpResponse({ body: pessoaFisicaCollection })));
      const expectedCollection: IPessoaFisica[] = [pessoaFisica, ...pessoaFisicaCollection];
      jest.spyOn(pessoaFisicaService, 'addPessoaFisicaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ socio });
      comp.ngOnInit();

      expect(pessoaFisicaService.query).toHaveBeenCalled();
      expect(pessoaFisicaService.addPessoaFisicaToCollectionIfMissing).toHaveBeenCalledWith(pessoaFisicaCollection, pessoaFisica);
      expect(comp.pessoaFisicasCollection).toEqual(expectedCollection);
    });

    it('Should call Empresa query and add missing value', () => {
      const socio: ISocio = { id: 456 };
      const empresa: IEmpresa = { id: 8169 };
      socio.empresa = empresa;

      const empresaCollection: IEmpresa[] = [{ id: 16979 }];
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
      const pessoaFisica: IPessoaFisica = { id: 12066 };
      socio.pessoaFisica = pessoaFisica;
      const empresa: IEmpresa = { id: 15409 };
      socio.empresa = empresa;

      activatedRoute.data = of({ socio });
      comp.ngOnInit();

      expect(comp.pessoaFisicasCollection).toContain(pessoaFisica);
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
    describe('comparePessoaFisica', () => {
      it('Should forward to pessoaFisicaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(pessoaFisicaService, 'comparePessoaFisica');
        comp.comparePessoaFisica(entity, entity2);
        expect(pessoaFisicaService.comparePessoaFisica).toHaveBeenCalledWith(entity, entity2);
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
