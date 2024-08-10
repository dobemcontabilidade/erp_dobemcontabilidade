import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { FrequenciaService } from '../service/frequencia.service';
import { IFrequencia } from '../frequencia.model';
import { FrequenciaFormService } from './frequencia-form.service';

import { FrequenciaUpdateComponent } from './frequencia-update.component';

describe('Frequencia Management Update Component', () => {
  let comp: FrequenciaUpdateComponent;
  let fixture: ComponentFixture<FrequenciaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let frequenciaFormService: FrequenciaFormService;
  let frequenciaService: FrequenciaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FrequenciaUpdateComponent],
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
      .overrideTemplate(FrequenciaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FrequenciaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    frequenciaFormService = TestBed.inject(FrequenciaFormService);
    frequenciaService = TestBed.inject(FrequenciaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const frequencia: IFrequencia = { id: 456 };

      activatedRoute.data = of({ frequencia });
      comp.ngOnInit();

      expect(comp.frequencia).toEqual(frequencia);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFrequencia>>();
      const frequencia = { id: 123 };
      jest.spyOn(frequenciaFormService, 'getFrequencia').mockReturnValue(frequencia);
      jest.spyOn(frequenciaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ frequencia });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: frequencia }));
      saveSubject.complete();

      // THEN
      expect(frequenciaFormService.getFrequencia).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(frequenciaService.update).toHaveBeenCalledWith(expect.objectContaining(frequencia));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFrequencia>>();
      const frequencia = { id: 123 };
      jest.spyOn(frequenciaFormService, 'getFrequencia').mockReturnValue({ id: null });
      jest.spyOn(frequenciaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ frequencia: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: frequencia }));
      saveSubject.complete();

      // THEN
      expect(frequenciaFormService.getFrequencia).toHaveBeenCalled();
      expect(frequenciaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFrequencia>>();
      const frequencia = { id: 123 };
      jest.spyOn(frequenciaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ frequencia });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(frequenciaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
