import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { CertificadoDigitalService } from '../service/certificado-digital.service';
import { ICertificadoDigital } from '../certificado-digital.model';
import { CertificadoDigitalFormService } from './certificado-digital-form.service';

import { CertificadoDigitalUpdateComponent } from './certificado-digital-update.component';

describe('CertificadoDigital Management Update Component', () => {
  let comp: CertificadoDigitalUpdateComponent;
  let fixture: ComponentFixture<CertificadoDigitalUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let certificadoDigitalFormService: CertificadoDigitalFormService;
  let certificadoDigitalService: CertificadoDigitalService;
  let empresaService: EmpresaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CertificadoDigitalUpdateComponent],
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
      .overrideTemplate(CertificadoDigitalUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CertificadoDigitalUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    certificadoDigitalFormService = TestBed.inject(CertificadoDigitalFormService);
    certificadoDigitalService = TestBed.inject(CertificadoDigitalService);
    empresaService = TestBed.inject(EmpresaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Empresa query and add missing value', () => {
      const certificadoDigital: ICertificadoDigital = { id: 456 };
      const empresa: IEmpresa = { id: 1364 };
      certificadoDigital.empresa = empresa;

      const empresaCollection: IEmpresa[] = [{ id: 23892 }];
      jest.spyOn(empresaService, 'query').mockReturnValue(of(new HttpResponse({ body: empresaCollection })));
      const additionalEmpresas = [empresa];
      const expectedCollection: IEmpresa[] = [...additionalEmpresas, ...empresaCollection];
      jest.spyOn(empresaService, 'addEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ certificadoDigital });
      comp.ngOnInit();

      expect(empresaService.query).toHaveBeenCalled();
      expect(empresaService.addEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        empresaCollection,
        ...additionalEmpresas.map(expect.objectContaining),
      );
      expect(comp.empresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const certificadoDigital: ICertificadoDigital = { id: 456 };
      const empresa: IEmpresa = { id: 621 };
      certificadoDigital.empresa = empresa;

      activatedRoute.data = of({ certificadoDigital });
      comp.ngOnInit();

      expect(comp.empresasSharedCollection).toContain(empresa);
      expect(comp.certificadoDigital).toEqual(certificadoDigital);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICertificadoDigital>>();
      const certificadoDigital = { id: 123 };
      jest.spyOn(certificadoDigitalFormService, 'getCertificadoDigital').mockReturnValue(certificadoDigital);
      jest.spyOn(certificadoDigitalService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ certificadoDigital });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: certificadoDigital }));
      saveSubject.complete();

      // THEN
      expect(certificadoDigitalFormService.getCertificadoDigital).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(certificadoDigitalService.update).toHaveBeenCalledWith(expect.objectContaining(certificadoDigital));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICertificadoDigital>>();
      const certificadoDigital = { id: 123 };
      jest.spyOn(certificadoDigitalFormService, 'getCertificadoDigital').mockReturnValue({ id: null });
      jest.spyOn(certificadoDigitalService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ certificadoDigital: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: certificadoDigital }));
      saveSubject.complete();

      // THEN
      expect(certificadoDigitalFormService.getCertificadoDigital).toHaveBeenCalled();
      expect(certificadoDigitalService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICertificadoDigital>>();
      const certificadoDigital = { id: 123 };
      jest.spyOn(certificadoDigitalService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ certificadoDigital });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(certificadoDigitalService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
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
