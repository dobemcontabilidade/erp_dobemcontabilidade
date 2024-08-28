import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

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

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const certificadoDigital: ICertificadoDigital = { id: 456 };

      activatedRoute.data = of({ certificadoDigital });
      comp.ngOnInit();

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
});
