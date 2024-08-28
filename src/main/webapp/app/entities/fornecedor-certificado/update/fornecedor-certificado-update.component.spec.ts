import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { FornecedorCertificadoService } from '../service/fornecedor-certificado.service';
import { IFornecedorCertificado } from '../fornecedor-certificado.model';
import { FornecedorCertificadoFormService } from './fornecedor-certificado-form.service';

import { FornecedorCertificadoUpdateComponent } from './fornecedor-certificado-update.component';

describe('FornecedorCertificado Management Update Component', () => {
  let comp: FornecedorCertificadoUpdateComponent;
  let fixture: ComponentFixture<FornecedorCertificadoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fornecedorCertificadoFormService: FornecedorCertificadoFormService;
  let fornecedorCertificadoService: FornecedorCertificadoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FornecedorCertificadoUpdateComponent],
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
      .overrideTemplate(FornecedorCertificadoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FornecedorCertificadoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fornecedorCertificadoFormService = TestBed.inject(FornecedorCertificadoFormService);
    fornecedorCertificadoService = TestBed.inject(FornecedorCertificadoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const fornecedorCertificado: IFornecedorCertificado = { id: 456 };

      activatedRoute.data = of({ fornecedorCertificado });
      comp.ngOnInit();

      expect(comp.fornecedorCertificado).toEqual(fornecedorCertificado);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFornecedorCertificado>>();
      const fornecedorCertificado = { id: 123 };
      jest.spyOn(fornecedorCertificadoFormService, 'getFornecedorCertificado').mockReturnValue(fornecedorCertificado);
      jest.spyOn(fornecedorCertificadoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fornecedorCertificado });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fornecedorCertificado }));
      saveSubject.complete();

      // THEN
      expect(fornecedorCertificadoFormService.getFornecedorCertificado).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(fornecedorCertificadoService.update).toHaveBeenCalledWith(expect.objectContaining(fornecedorCertificado));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFornecedorCertificado>>();
      const fornecedorCertificado = { id: 123 };
      jest.spyOn(fornecedorCertificadoFormService, 'getFornecedorCertificado').mockReturnValue({ id: null });
      jest.spyOn(fornecedorCertificadoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fornecedorCertificado: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fornecedorCertificado }));
      saveSubject.complete();

      // THEN
      expect(fornecedorCertificadoFormService.getFornecedorCertificado).toHaveBeenCalled();
      expect(fornecedorCertificadoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFornecedorCertificado>>();
      const fornecedorCertificado = { id: 123 };
      jest.spyOn(fornecedorCertificadoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fornecedorCertificado });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fornecedorCertificadoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
