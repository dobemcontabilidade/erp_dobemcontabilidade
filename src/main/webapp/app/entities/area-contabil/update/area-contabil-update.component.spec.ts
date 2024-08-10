import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { AreaContabilService } from '../service/area-contabil.service';
import { IAreaContabil } from '../area-contabil.model';
import { AreaContabilFormService } from './area-contabil-form.service';

import { AreaContabilUpdateComponent } from './area-contabil-update.component';

describe('AreaContabil Management Update Component', () => {
  let comp: AreaContabilUpdateComponent;
  let fixture: ComponentFixture<AreaContabilUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let areaContabilFormService: AreaContabilFormService;
  let areaContabilService: AreaContabilService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AreaContabilUpdateComponent],
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
      .overrideTemplate(AreaContabilUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AreaContabilUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    areaContabilFormService = TestBed.inject(AreaContabilFormService);
    areaContabilService = TestBed.inject(AreaContabilService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const areaContabil: IAreaContabil = { id: 456 };

      activatedRoute.data = of({ areaContabil });
      comp.ngOnInit();

      expect(comp.areaContabil).toEqual(areaContabil);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAreaContabil>>();
      const areaContabil = { id: 123 };
      jest.spyOn(areaContabilFormService, 'getAreaContabil').mockReturnValue(areaContabil);
      jest.spyOn(areaContabilService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ areaContabil });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: areaContabil }));
      saveSubject.complete();

      // THEN
      expect(areaContabilFormService.getAreaContabil).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(areaContabilService.update).toHaveBeenCalledWith(expect.objectContaining(areaContabil));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAreaContabil>>();
      const areaContabil = { id: 123 };
      jest.spyOn(areaContabilFormService, 'getAreaContabil').mockReturnValue({ id: null });
      jest.spyOn(areaContabilService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ areaContabil: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: areaContabil }));
      saveSubject.complete();

      // THEN
      expect(areaContabilFormService.getAreaContabil).toHaveBeenCalled();
      expect(areaContabilService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAreaContabil>>();
      const areaContabil = { id: 123 };
      jest.spyOn(areaContabilService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ areaContabil });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(areaContabilService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
