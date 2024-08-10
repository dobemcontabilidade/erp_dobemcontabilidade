import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { TipoDenunciaService } from '../service/tipo-denuncia.service';
import { ITipoDenuncia } from '../tipo-denuncia.model';
import { TipoDenunciaFormService } from './tipo-denuncia-form.service';

import { TipoDenunciaUpdateComponent } from './tipo-denuncia-update.component';

describe('TipoDenuncia Management Update Component', () => {
  let comp: TipoDenunciaUpdateComponent;
  let fixture: ComponentFixture<TipoDenunciaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tipoDenunciaFormService: TipoDenunciaFormService;
  let tipoDenunciaService: TipoDenunciaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TipoDenunciaUpdateComponent],
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
      .overrideTemplate(TipoDenunciaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TipoDenunciaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tipoDenunciaFormService = TestBed.inject(TipoDenunciaFormService);
    tipoDenunciaService = TestBed.inject(TipoDenunciaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const tipoDenuncia: ITipoDenuncia = { id: 456 };

      activatedRoute.data = of({ tipoDenuncia });
      comp.ngOnInit();

      expect(comp.tipoDenuncia).toEqual(tipoDenuncia);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITipoDenuncia>>();
      const tipoDenuncia = { id: 123 };
      jest.spyOn(tipoDenunciaFormService, 'getTipoDenuncia').mockReturnValue(tipoDenuncia);
      jest.spyOn(tipoDenunciaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tipoDenuncia });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tipoDenuncia }));
      saveSubject.complete();

      // THEN
      expect(tipoDenunciaFormService.getTipoDenuncia).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(tipoDenunciaService.update).toHaveBeenCalledWith(expect.objectContaining(tipoDenuncia));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITipoDenuncia>>();
      const tipoDenuncia = { id: 123 };
      jest.spyOn(tipoDenunciaFormService, 'getTipoDenuncia').mockReturnValue({ id: null });
      jest.spyOn(tipoDenunciaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tipoDenuncia: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tipoDenuncia }));
      saveSubject.complete();

      // THEN
      expect(tipoDenunciaFormService.getTipoDenuncia).toHaveBeenCalled();
      expect(tipoDenunciaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITipoDenuncia>>();
      const tipoDenuncia = { id: 123 };
      jest.spyOn(tipoDenunciaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tipoDenuncia });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tipoDenunciaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
