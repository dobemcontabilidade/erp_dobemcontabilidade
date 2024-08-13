import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { SistemaDetailComponent } from './sistema-detail.component';

describe('Sistema Management Detail Component', () => {
  let comp: SistemaDetailComponent;
  let fixture: ComponentFixture<SistemaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SistemaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: SistemaDetailComponent,
              resolve: { sistema: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SistemaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SistemaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load sistema on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SistemaDetailComponent);

      // THEN
      expect(instance.sistema()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
